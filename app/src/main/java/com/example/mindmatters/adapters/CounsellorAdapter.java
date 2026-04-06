package com.example.mindmatters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.Counsellor;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter that binds counsellor summary data into the student home list.
 * Outstanding issues: profile images are still using a shared placeholder instead of remote image loading.
 */
public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.CounsellorViewHolder> {
    private final List<Counsellor> counsellors = new ArrayList<>();
    private final CounsellorActionListener listener;

    // Stores the callback used when a counsellor card action is pressed.
    public CounsellorAdapter(CounsellorActionListener listener) {
        this.listener = listener;
    }

    // Replaces the displayed counsellor list.
    public void submitList(List<Counsellor> users) {
        counsellors.clear();
        counsellors.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    // Inflates a counsellor summary card.
    public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counsellor_card, parent, false);
        return new CounsellorViewHolder(view);
    }

    @Override
    // Binds one counsellor into the summary card.
    public void onBindViewHolder(@NonNull CounsellorViewHolder holder, int position) {
        Counsellor counsellor = counsellors.get(position);
        holder.nameText.setText(counsellor.getName());
        holder.specialityText.setText("Specialization: " + counsellor.getProfile().getSpeciality());
        holder.modesText.setText(counsellor.getSchedule().getMeetingModesText());
        holder.bookButton.setOnClickListener(v -> listener.onBookSession(counsellor));
        holder.detailsButton.setOnClickListener(v -> listener.onViewDetails(counsellor));
    }

    @Override
    // Returns the number of counsellor cards to show.
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
        void onBookSession(Counsellor counsellor);
        void onViewDetails(Counsellor counsellor);
    }
}
