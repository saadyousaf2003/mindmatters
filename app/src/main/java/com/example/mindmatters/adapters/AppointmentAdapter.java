package com.example.mindmatters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.Appointment;
import com.example.mindmatters.utils.StudentBookingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter that renders upcoming student appointments as simple cards.
 * Outstanding issues: cards only support viewing details and do not expose richer appointment states yet.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private final List<Appointment> appointments = new ArrayList<>();
    private final AppointmentActionListener listener;

    // Stores the callback used when a card action is pressed.
    public AppointmentAdapter(AppointmentActionListener listener) {
        this.listener = listener;
    }

    // Replaces the adapter data with a fresh appointment list.
    public void submitList(List<Appointment> updatedAppointments) {
        appointments.clear();
        appointments.addAll(updatedAppointments);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    // Inflates a single appointment card row.
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_card, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    // Binds one appointment into the card row.
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.counsellorNameText.setText(appointment.getCounsellorName());
        holder.datetimeText.setText("Meeting block: " + StudentBookingUtils.buildAppointmentDateTime(
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        ));
        holder.modeText.setText("Mode: " + appointment.getMeetingMode());
        holder.statusText.setText(appointment.getStatus());
        holder.detailsButton.setOnClickListener(v -> listener.onViewDetails(appointment));
    }

    @Override
    // Returns the number of appointment cards to show.
    public int getItemCount() {
        return appointments.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        final TextView counsellorNameText;
        final TextView datetimeText;
        final TextView modeText;
        final TextView statusText;
        final Button detailsButton;

        AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            counsellorNameText = itemView.findViewById(R.id.appointment_counsellor_name);
            datetimeText = itemView.findViewById(R.id.appointment_datetime);
            modeText = itemView.findViewById(R.id.appointment_mode);
            statusText = itemView.findViewById(R.id.appointment_status);
            detailsButton = itemView.findViewById(R.id.appointment_details_button);
        }
    }

    public interface AppointmentActionListener {
        void onViewDetails(Appointment appointment);
    }
}
