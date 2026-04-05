package com.example.mindmatters.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CounsellorSchedule implements Serializable {
    private boolean supportsOnline;
    private boolean supportsInPerson;
    private List<AvailableSlot> availableSlots;

    public CounsellorSchedule() {
        supportsOnline = true;
        supportsInPerson = true;
        availableSlots = new ArrayList<>();
    }

    public boolean isSupportsOnline() {
        return supportsOnline;
    }

    public void setSupportsOnline(boolean supportsOnline) {
        this.supportsOnline = supportsOnline;
    }

    public boolean isSupportsInPerson() {
        return supportsInPerson;
    }

    public void setSupportsInPerson(boolean supportsInPerson) {
        this.supportsInPerson = supportsInPerson;
    }

    public List<AvailableSlot> getAvailableSlots() {
        return availableSlots == null ? new ArrayList<>() : availableSlots;
    }

    public void setAvailableSlots(List<AvailableSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public boolean canAcceptBookings() {
        return supportsOnline || supportsInPerson;
    }

    public List<String> getAvailableMeetingModes() {
        List<String> meetingModes = new ArrayList<>();
        if (supportsOnline) {
            meetingModes.add("Online");
        }
        if (supportsInPerson) {
            meetingModes.add("In Person");
        }
        return meetingModes;
    }

    public String getMeetingModesText() {
        List<String> meetingModes = getAvailableMeetingModes();
        if (meetingModes.isEmpty()) {
            return "Meeting modes: Not available";
        }
        return "Meeting modes: " + String.join(", ", meetingModes);
    }

    public String buildAvailableSlotsText() {
        if (getAvailableSlots().isEmpty()) {
            return "Available time slots:\nNo slots added yet.";
        }

        StringBuilder slotBuilder = new StringBuilder("Available time slots:\n");
        for (AvailableSlot slot : getAvailableSlots()) {
            slotBuilder.append(slot.getDayOfWeek())
                    .append(" ")
                    .append(slot.getStartTime())
                    .append(" - ")
                    .append(slot.getEndTime())
                    .append("\n");
        }
        return slotBuilder.toString().trim();
    }
}
