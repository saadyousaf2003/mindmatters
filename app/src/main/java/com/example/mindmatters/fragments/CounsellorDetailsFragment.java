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
import com.example.mindmatters.classes.Counsellor;

/**
 * Detail fragment that shows the full student-facing profile and schedule for a counsellor.
 * Outstanding issues: this screen is read-only and still uses placeholder-friendly fallback data.
 */
public class CounsellorDetailsFragment extends Fragment {
    private static final String ARG_COUNSELLOR = "arg_counsellor";

    // Packs a counsellor into a details fragment instance.
    public static CounsellorDetailsFragment newInstance(Counsellor counsellor) {
        CounsellorDetailsFragment fragment = new CounsellorDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNSELLOR, counsellor);
        fragment.setArguments(args);
        return fragment;
    }

    // Required empty constructor for fragment recreation.
    public CounsellorDetailsFragment() {
    }

    @Nullable
    @Override
    // Inflates the counsellor details layout.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_counsellor_details, container, false);
    }

    @Override
    // Binds counsellor profile and schedule data into the screen.
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Counsellor counsellor = null;
        if (getArguments() != null) {
            counsellor = (Counsellor) getArguments().getSerializable(ARG_COUNSELLOR);
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
        specialityText.setText(counsellor.getProfile().getSpeciality());
        experienceText.setText("Experience: " + counsellor.getProfile().getYearsExperience() + " years");
        bioText.setText("Bio: " + counsellor.getProfile().getBio());
        slotsText.setText(counsellor.getSchedule().buildAvailableSlotsText());
    }
}
