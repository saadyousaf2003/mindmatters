package com.example.mindmatters.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedule model that owns meeting mode availability and the counsellor's bookable time slots.
 * Outstanding issues: it does not yet support exceptions, real-time availability updates, or slot locking by itself.
 */
public class CounsellorSchedule implements Serializable {
    private boolean supportsOnline;
    private boolean supportsInPerson;
    private List<AvailableSlot> availableSlots;

    // Creates default schedule values for a counsellor.
    public CounsellorSchedule() {
        supportsOnline = true;
        supportsInPerson = true;
        availableSlots = new ArrayList<>();
    }

    // Gets and updates the available meeting modes and slots for this counsellor.
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

    // Returns whether the current schedule can accept a new booking.
    public boolean canAcceptBookings() {
        return supportsOnline || supportsInPerson;
    }

    // Builds simple text labels used by the current student UI.
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
