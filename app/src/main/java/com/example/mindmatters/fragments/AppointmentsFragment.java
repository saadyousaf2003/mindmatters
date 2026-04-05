package com.example.mindmatters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.activities.StudentHomeActivity;
import com.example.mindmatters.adapters.AppointmentAdapter;
import com.example.mindmatters.classes.Appointment;
import com.example.mindmatters.classes.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppointmentsFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private AppointmentAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyText;

    public AppointmentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.appointments_recycler);
        progressBar = view.findViewById(R.id.appointments_progress);
        emptyText = view.findViewById(R.id.empty_appointments_text);
        adapter = new AppointmentAdapter(appointment -> {
            if (getActivity() instanceof StudentHomeActivity) {
                ((StudentHomeActivity) getActivity()).openAppointmentDetails(appointment);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAppointments();
    }

    private void loadAppointments() {
        if (auth.getCurrentUser() == null) {
            emptyText.setVisibility(View.VISIBLE);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
        db.collection("appointments")
                .whereEqualTo("studentId", auth.getCurrentUser().getUid())
                .whereEqualTo("status", "BOOKED")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    Student currentStudent = new Student();
                    currentStudent.setUserId(auth.getCurrentUser().getUid());
                    List<Appointment> appointments = new ArrayList<>();
                    for (var document : queryDocumentSnapshots.getDocuments()) {
                        Appointment appointment = document.toObject(Appointment.class);
                        if (currentStudent.canTrackAppointment(appointment)) {
                            appointments.add(appointment);
                        }
                    }
                    appointments.sort(Comparator
                            .comparing(Appointment::getAppointmentDate)
                            .thenComparing(Appointment::getStartTime));
                    adapter.submitList(appointments);
                    emptyText.setVisibility(appointments.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Failed to load appointments.", Toast.LENGTH_SHORT).show();
                });
    }
}
