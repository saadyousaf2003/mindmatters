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
import com.example.mindmatters.adapters.CounsellorAdapter;
import com.example.mindmatters.classes.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CounsellorAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyText;

    public StudentHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.counsellors_recycler);
        progressBar = view.findViewById(R.id.counsellors_progress);
        emptyText = view.findViewById(R.id.empty_counsellors_text);
        adapter = new CounsellorAdapter(new CounsellorAdapter.CounsellorActionListener() {
            @Override
            public void onBookSession(User counsellor) {
                if (getActivity() instanceof StudentHomeActivity) {
                    ((StudentHomeActivity) getActivity()).openBooking(counsellor);
                }
            }

            @Override
            public void onViewDetails(User counsellor) {
                if (getActivity() instanceof StudentHomeActivity) {
                    ((StudentHomeActivity) getActivity()).openCounsellorDetails(counsellor);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        loadCounsellors();
    }

    private void loadCounsellors() {
        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
        db.collection("users")
                .whereEqualTo("type", "counsellor")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    List<User> counsellors = new ArrayList<>();
                    for (var document : queryDocumentSnapshots.getDocuments()) {
                        User user = document.toObject(User.class);
                        if (user != null) {
                            counsellors.add(user);
                        }
                    }
                    adapter.submitList(counsellors);
                    emptyText.setVisibility(counsellors.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Failed to load counsellors.", Toast.LENGTH_SHORT).show();
                });
    }
}
