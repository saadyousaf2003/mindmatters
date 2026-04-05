package com.example.mindmatters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.AvailableSlot;
import com.example.mindmatters.classes.User;

public class CounsellorDetailsFragment extends Fragment {
    private static final String ARG_COUNSELLOR = "arg_counsellor";

    public static CounsellorDetailsFragment newInstance(User counsellor) {
        CounsellorDetailsFragment fragment = new CounsellorDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNSELLOR, counsellor);
        fragment.setArguments(args);
        return fragment;
    }

    public CounsellorDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_counsellor_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User counsellor = null;
        if (getArguments() != null) {
            counsellor = (User) getArguments().getSerializable(ARG_COUNSELLOR);
        }
        if (counsellor == null) {
            return;
        }

        TextView nameText = view.findViewById(R.id.details_name);
        TextView specialityText = view.findViewById(R.id.details_speciality);
        TextView experienceText = view.findViewById(R.id.details_experience);
        TextView bioText = view.findViewById(R.id.details_bio);
        TextView slotsText = view.findViewById(R.id.details_slots);

        nameText.setText(counsellor.getName());
        specialityText.setText(counsellor.getSpeciality());
        experienceText.setText("Experience: " + counsellor.getYearsExperience() + " years");
        bioText.setText("Bio: " + counsellor.getBio());

        StringBuilder slotBuilder = new StringBuilder("Available time slots:\n");
        if (counsellor.getAvailableSlots().isEmpty()) {
            slotBuilder.append("No slots added yet.");
        } else {
            for (AvailableSlot slot : counsellor.getAvailableSlots()) {
                slotBuilder.append(slot.getDayOfWeek())
                        .append(" ")
                        .append(slot.getStartTime())
                        .append(" - ")
                        .append(slot.getEndTime())
                        .append("\n");
            }
        }
        slotsText.setText(slotBuilder.toString().trim());
    }
}
