package com.example.mindmatters.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mindmatters.R;

/**
 * Placeholder fragment reserved for future student account and profile management features.
 * Outstanding issues: the entire student profile/settings flow is still unimplemented.
 */
public class StudentProfilePlaceholderFragment extends Fragment {
    // Required empty constructor for fragment recreation.
    public StudentProfilePlaceholderFragment() {
    }

    @Nullable
    @Override
    // Inflates the placeholder profile tab.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_profile_placeholder, container, false);
    }
}
