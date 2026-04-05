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

public class BookingSlotAdapter extends RecyclerView.Adapter<BookingSlotAdapter.SlotViewHolder> {
    private final List<StudentBookingUtils.DisplaySlot> displaySlots = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;

    public void submitList(List<StudentBookingUtils.DisplaySlot> slots) {
        displaySlots.clear();
        displaySlots.addAll(slots);
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    public StudentBookingUtils.DisplaySlot getSelectedSlot() {
        if (selectedPosition < 0 || selectedPosition >= displaySlots.size()) {
            return null;
        }
        return displaySlots.get(selectedPosition);
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot_card, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        StudentBookingUtils.DisplaySlot slot = displaySlots.get(position);
        holder.dayText.setText(slot.getDayLabel());
        holder.timeText.setText(slot.getStartTime() + " - " + slot.getEndTime());
        holder.radioButton.setChecked(position == selectedPosition);
        holder.itemView.setOnClickListener(v -> selectPosition(position));
        holder.radioButton.setOnClickListener(v -> selectPosition(position));
    }

    @Override
    public int getItemCount() {
        return displaySlots.size();
    }

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
