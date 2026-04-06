package com.example.mindmatters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmatters.R;
import com.example.mindmatters.utils.StudentBookingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter that presents weekly booking slots using single-selection list behavior.
 * Outstanding issues: booked or expired slots are not visually disabled inside the list itself.
 */
public class BookingSlotAdapter extends RecyclerView.Adapter<BookingSlotAdapter.SlotViewHolder> {
    private final List<StudentBookingUtils.DisplaySlot> displaySlots = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;

    // Replaces the current slot list and clears the previous selection.
    public void submitList(List<StudentBookingUtils.DisplaySlot> slots) {
        displaySlots.clear();
        displaySlots.addAll(slots);
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    // Returns the currently selected slot, if one exists.
    public StudentBookingUtils.DisplaySlot getSelectedSlot() {
        if (selectedPosition < 0 || selectedPosition >= displaySlots.size()) {
            return null;
        }
        return displaySlots.get(selectedPosition);
    }

    @NonNull
    @Override
    // Inflates a slot row for the booking list.
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot_card, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    // Binds slot text and selection state into the row.
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        StudentBookingUtils.DisplaySlot slot = displaySlots.get(position);
        holder.dayText.setText(slot.getDayLabel());
        holder.timeText.setText(slot.getStartTime() + " - " + slot.getEndTime());
        holder.radioButton.setChecked(position == selectedPosition);
        holder.itemView.setOnClickListener(v -> selectPosition(position));
        holder.radioButton.setOnClickListener(v -> selectPosition(position));
    }

    @Override
    // Returns the number of slots in the list.
    public int getItemCount() {
        return displaySlots.size();
    }

    // Updates the single selected row in the list.
    private void selectPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        if (previousPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousPosition);
        }
        notifyItemChanged(position);
    }

    static class SlotViewHolder extends RecyclerView.ViewHolder {
        final RadioButton radioButton;
        final TextView dayText;
        final TextView timeText;

        SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.slot_radio);
            dayText = itemView.findViewById(R.id.slot_day);
            timeText = itemView.findViewById(R.id.slot_time);
        }
    }
}
