package com.example.mindmatters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.Appointment;
import com.example.mindmatters.utils.StudentBookingUtils;

public class AppointmentDetailsFragment extends Fragment {
    private static final String ARG_COUNSELLOR_NAME = "arg_counsellor_name";
    private static final String ARG_APPOINTMENT_DATE = "arg_appointment_date";
    private static final String ARG_START_TIME = "arg_start_time";
    private static final String ARG_END_TIME = "arg_end_time";
    private static final String ARG_MEETING_MODE = "arg_meeting_mode";

    public static AppointmentDetailsFragment newInstance(Appointment appointment) {
        AppointmentDetailsFragment fragment = new AppointmentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COUNSELLOR_NAME, appointment.getCounsellorName());
        args.putString(ARG_APPOINTMENT_DATE, appointment.getAppointmentDate());
        args.putString(ARG_START_TIME, appointment.getStartTime());
        args.putString(ARG_END_TIME, appointment.getEndTime());
        args.putString(ARG_MEETING_MODE, appointment.getMeetingMode());
        fragment.setArguments(args);
        return fragment;
    }

    public AppointmentDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        TextView titleText = view.findViewById(R.id.appointment_details_title);
        TextView datetimeText = view.findViewById(R.id.appointment_details_datetime);
        TextView modeText = view.findViewById(R.id.appointment_details_mode);
        Button rescheduleButton = view.findViewById(R.id.reschedule_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        Button presessionButton = view.findViewById(R.id.presession_button);

        titleText.setText(args.getString(ARG_COUNSELLOR_NAME, ""));
        datetimeText.setText(StudentBookingUtils.buildAppointmentDateTime(
                args.getString(ARG_APPOINTMENT_DATE, ""),
                args.getString(ARG_START_TIME, ""),
                args.getString(ARG_END_TIME, "")
        ));
        modeText.setText("Meeting mode: " + args.getString(ARG_MEETING_MODE, ""));

        View.OnClickListener placeholderListener = v ->
                Toast.makeText(requireContext(), "This action is ready for future implementation.", Toast.LENGTH_SHORT).show();
        rescheduleButton.setOnClickListener(placeholderListener);
        cancelButton.setOnClickListener(placeholderListener);
        presessionButton.setOnClickListener(placeholderListener);
    }
}
