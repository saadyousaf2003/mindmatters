package com.example.mindmatters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.User;

import java.util.ArrayList;
import java.util.List;

public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.CounsellorViewHolder> {
    private final List<User> counsellors = new ArrayList<>();
    private final CounsellorActionListener listener;

    public CounsellorAdapter(CounsellorActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<User> users) {
        counsellors.clear();
        counsellors.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counsellor_card, parent, false);
        return new CounsellorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounsellorViewHolder holder, int position) {
        User counsellor = counsellors.get(position);
        holder.nameText.setText(counsellor.getName());
        holder.specialityText.setText("Specialization: " + counsellor.getSpeciality());
        holder.modesText.setText(buildMeetingModesText(counsellor));
        holder.bookButton.setOnClickListener(v -> listener.onBookSession(counsellor));
        holder.detailsButton.setOnClickListener(v -> listener.onViewDetails(counsellor));
    }

    private String buildMeetingModesText(User counsellor) {
        if (counsellor.isSupportsOnline() && counsellor.isSupportsInPerson()) {
            return "Meeting modes: Online, In Person";
        }
        if (counsellor.isSupportsOnline()) {
            return "Meeting modes: Online";
        }
        if (counsellor.isSupportsInPerson()) {
            return "Meeting modes: In Person";
        }
        return "Meeting modes: Not available";
    }

    @Override
    public int getItemCount() {
        return counsellors.size();
    }

    static class CounsellorViewHolder extends RecyclerView.ViewHolder {
        final TextView nameText;
        final TextView specialityText;
        final TextView modesText;
        final Button detailsButton;
        final Button bookButton;

        CounsellorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.counsellor_name);
            specialityText = itemView.findViewById(R.id.counsellor_speciality);
            modesText = itemView.findViewById(R.id.counsellor_modes);
            detailsButton = itemView.findViewById(R.id.counsellor_details_button);
            bookButton = itemView.findViewById(R.id.counsellor_book_button);
        }
    }

    public interface CounsellorActionListener {
        void onBookSession(User counsellor);
        void onViewDetails(User counsellor);
    }
}
