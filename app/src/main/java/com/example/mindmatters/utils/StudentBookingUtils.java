package com.example.mindmatters.utils;

import com.example.mindmatters.classes.AvailableSlot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class StudentBookingUtils {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE d MMM", Locale.getDefault());
    public static final DateTimeFormatter DISPLAY_DATE_LONG_FORMAT = DateTimeFormatter.ofPattern("EEEE d MMM yyyy", Locale.getDefault());

    private StudentBookingUtils() {
    }

    public static LocalDate getStartOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static String buildWeekLabel(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        return startDate.format(DISPLAY_DATE_FORMAT) + " - " + endDate.format(DISPLAY_DATE_FORMAT);
    }

    public static List<DisplaySlot> toDisplaySlots(List<AvailableSlot> availableSlots, LocalDate weekStart) {
        List<DisplaySlot> displaySlots = new ArrayList<>();
        if (availableSlots == null) {
            return displaySlots;
        }

        for (AvailableSlot slot : availableSlots) {
            if (slot == null || slot.getDayOfWeek() == null || slot.getStartTime() == null || slot.getEndTime() == null) {
                continue;
            }

            DayOfWeek dayOfWeek;
            try {
                dayOfWeek = DayOfWeek.valueOf(slot.getDayOfWeek().toUpperCase(Locale.US));
            } catch (IllegalArgumentException exception) {
                continue;
            }

            LocalDate slotDate = weekStart.with(dayOfWeek);
            String dayLabel = slotDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + slotDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
            displaySlots.add(new DisplaySlot(slotDate.format(DATE_FORMAT), dayLabel, slot.getStartTime(), slot.getEndTime()));
        }

        return displaySlots;
    }

    public static String buildAppointmentDateTime(String appointmentDate, String startTime, String endTime) {
        LocalDate parsedDate = LocalDate.parse(appointmentDate, DATE_FORMAT);
        return parsedDate.format(DISPLAY_DATE_LONG_FORMAT) + " | " + startTime + " - " + endTime;
    }

    public static String buildSlotId(String counsellorId, String appointmentDate, String startTime, String endTime) {
        return (counsellorId + "_" + appointmentDate + "_" + startTime + "_" + endTime).replace(" ", "").replace(":", "-");
    }

    public static String buildStudentLockId(String studentId, String appointmentDate, String startTime, String endTime) {
        return (studentId + "_" + appointmentDate + "_" + startTime + "_" + endTime).replace(" ", "").replace(":", "-");
    }

    public static class DisplaySlot {
        private final String appointmentDate;
        private final String dayLabel;
        private final String startTime;
        private final String endTime;

        public DisplaySlot(String appointmentDate, String dayLabel, String startTime, String endTime) {
            this.appointmentDate = appointmentDate;
            this.dayLabel = dayLabel;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public String getDayLabel() {
            return dayLabel;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }
    }
}
