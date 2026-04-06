package com.example.mindmatters.classes;

import java.io.Serializable;

/**
 * Value object representing a reusable counsellor availability block.
 */
public class AvailableSlot implements Serializable {
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    // Required empty constructor for Firestore object mapping.
    public AvailableSlot() {
    }

    // Creates a reusable weekly availability block.
    public AvailableSlot(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Gets and updates the day and time values for this slot.
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
