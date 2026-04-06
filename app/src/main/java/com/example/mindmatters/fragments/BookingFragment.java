package com.example.mindmatters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.activities.StudentHomeActivity;
import com.example.mindmatters.adapters.BookingSlotAdapter;
import com.example.mindmatters.classes.Appointment;
import com.example.mindmatters.classes.Counsellor;
import com.example.mindmatters.classes.Student;
import com.example.mindmatters.utils.StudentBookingUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student booking fragment that lets a user choose a meeting mode and a weekly time slot for a counsellor.
 * Outstanding issues: rescheduling reuse, loading states, and old flat Firestore counsellor records are not fully handled.
 */
public class BookingFragment extends Fragment {
    private static final String ARG_COUNSELLOR = "arg_counsellor";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private Counsellor counsellor;
    private Student currentStudent;
    private LocalDate selectedWeekStart;
    private BookingSlotAdapter slotAdapter;
    private Spinner meetingModeSpinner;
    private TextView weekRangeText;
    private TextView noSlotsText;
    private Button confirmBookingButton;

    // Packs a counsellor into a booking fragment instance.
    public static BookingFragment newInstance(Counsellor counsellor) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNSELLOR, counsellor);
        fragment.setArguments(args);
        return fragment;
    }

    // Required empty constructor for fragment recreation.
    public BookingFragment() {
    }

    @Nullable
    @Override
    // Inflates the booking layout.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    // Binds counsellor data, slot navigation, and booking actions.
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            counsellor = (Counsellor) getArguments().getSerializable(ARG_COUNSELLOR);
        }
        if (counsellor == null) {
            Toast.makeText(requireContext(), "Unable to load booking screen.", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
            return;
        }

        TextView titleText = view.findViewById(R.id.booking_title);
        TextView specialityText = view.findViewById(R.id.booking_speciality);
        meetingModeSpinner = view.findViewById(R.id.meeting_mode_spinner);
        weekRangeText = view.findViewById(R.id.week_range_text);
        noSlotsText = view.findViewById(R.id.no_slots_text);
        Button previousWeekButton = view.findViewById(R.id.previous_week_button);
        Button nextWeekButton = view.findViewById(R.id.next_week_button);
        confirmBookingButton = view.findViewById(R.id.confirm_booking_button);
        RecyclerView slotsRecycler = view.findViewById(R.id.slots_recycler);

        titleText.setText("Book with " + counsellor.getName());
        specialityText.setText("Specialization: " + counsellor.getProfile().getSpeciality());
        selectedWeekStart = StudentBookingUtils.getStartOfWeek(LocalDate.now());
        currentStudent = new Student();
        if (auth.getCurrentUser() != null) {
            currentStudent.setUserId(auth.getCurrentUser().getUid());
        }

        slotAdapter = new BookingSlotAdapter();
        slotsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        slotsRecycler.setAdapter(slotAdapter);

        setupMeetingModes();
        renderCurrentWeek();

        previousWeekButton.setOnClickListener(v -> {
            selectedWeekStart = selectedWeekStart.minusWeeks(1);
            renderCurrentWeek();
        });

        nextWeekButton.setOnClickListener(v -> {
            selectedWeekStart = selectedWeekStart.plusWeeks(1);
            renderCurrentWeek();
        });

        confirmBookingButton.setOnClickListener(v -> confirmBooking());
    }

    // Populates the meeting mode dropdown from the counsellor schedule.
    private void setupMeetingModes() {
        List<String> meetingModes = new ArrayList<>(counsellor.getSchedule().getAvailableMeetingModes());
        if (meetingModes.isEmpty()) {
            meetingModes.add("No meeting modes available");
            confirmBookingButton.setEnabled(false);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, meetingModes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingModeSpinner.setAdapter(adapter);
    }

    // Rebuilds the visible slots for the selected week.
    private void renderCurrentWeek() {
        weekRangeText.setText(StudentBookingUtils.buildWeekLabel(selectedWeekStart));
        List<StudentBookingUtils.DisplaySlot> displaySlots = StudentBookingUtils.toDisplaySlots(counsellor.getSchedule().getAvailableSlots(), selectedWeekStart);
        slotAdapter.submitList(displaySlots);
        noSlotsText.setVisibility(displaySlots.isEmpty() ? View.VISIBLE : View.GONE);
    }

    // Attempts to save a booking and its clash lock in Firestore.
    private void confirmBooking() {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(requireContext(), "Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!currentStudent.canBookAppointmentWith(counsellor)) {
            Toast.makeText(requireContext(), "This counsellor is not accepting bookings yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentBookingUtils.DisplaySlot selectedSlot = slotAdapter.getSelectedSlot();
        if (selectedSlot == null) {
            Toast.makeText(requireContext(), "Select a time slot first.", Toast.LENGTH_SHORT).show();
            return;
        }

        String meetingMode = meetingModeSpinner.getSelectedItem().toString();
        if ("No meeting modes available".equals(meetingMode)) {
            Toast.makeText(requireContext(), "This counsellor is not accepting bookings yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentId = auth.getCurrentUser().getUid();
        String appointmentId = StudentBookingUtils.buildSlotId(
                counsellor.getUserId(),
                selectedSlot.getAppointmentDate(),
                selectedSlot.getStartTime(),
                selectedSlot.getEndTime()
        );
        String studentLockId = StudentBookingUtils.buildStudentLockId(
                studentId,
                selectedSlot.getAppointmentDate(),
                selectedSlot.getStartTime(),
                selectedSlot.getEndTime()
        );

        DocumentReference appointmentRef = db.collection("appointments").document(appointmentId);
        DocumentReference studentLockRef = db.collection("student_booking_locks").document(studentLockId);
        confirmBookingButton.setEnabled(false);

        db.runTransaction(transaction -> {
            if (transaction.get(appointmentRef).exists()) {
                throw new IllegalStateException("This slot has already been booked.");
            }
            if (transaction.get(studentLockRef).exists()) {
                throw new IllegalStateException("You already have an appointment at this time.");
            }

            Appointment appointment = new Appointment();
            appointment.setAppointmentId(appointmentId);
            appointment.setStudentId(studentId);
            appointment.setCounsellorId(counsellor.getUserId());
            appointment.setCounsellorName(counsellor.getName());
            appointment.setCounsellorSpeciality(counsellor.getProfile().getSpeciality());
            appointment.setCounsellorImageUrl(counsellor.getProfile().getProfileImageUrl());
            appointment.setAppointmentDate(selectedSlot.getAppointmentDate());
            appointment.setStartTime(selectedSlot.getStartTime());
            appointment.setEndTime(selectedSlot.getEndTime());
            appointment.setMeetingMode(meetingMode);
            appointment.setStatus("BOOKED");
            appointment.setStudentLockId(studentLockId);
            appointment.setCreatedAt(Timestamp.now());

            Map<String, Object> lockData = new HashMap<>();
            lockData.put("appointmentId", appointmentId);
            lockData.put("studentId", studentId);
            lockData.put("appointmentDate", selectedSlot.getAppointmentDate());
            lockData.put("startTime", selectedSlot.getStartTime());
            lockData.put("endTime", selectedSlot.getEndTime());

            transaction.set(appointmentRef, appointment);
            transaction.set(studentLockRef, lockData);
            return null;
        }).addOnSuccessListener(unused -> {
            confirmBookingButton.setEnabled(true);
            Toast.makeText(requireContext(), "Appointment booked successfully.", Toast.LENGTH_SHORT).show();
            if (getActivity() instanceof StudentHomeActivity) {
                ((StudentHomeActivity) getActivity()).openAppointmentsTab();
            }
        }).addOnFailureListener(e -> {
            confirmBookingButton.setEnabled(true);
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
