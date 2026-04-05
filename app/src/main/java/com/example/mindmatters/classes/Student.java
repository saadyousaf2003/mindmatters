package com.example.mindmatters.classes;

import java.util.List;

public class Student extends User {
    public Student() {
        super();
        setType("student");
    }

    public Student(String userId, String name, String email) {
        super(userId, name, email, "student");
    }

    public boolean canViewCounsellors() {
        return true;
    }

    public boolean canBookAppointmentWith(Counsellor counsellor) {
        return counsellor != null && counsellor.canAcceptBookings();
    }

    public boolean canTrackAppointment(Appointment appointment) {
        return appointment != null && appointment.belongsToStudent(getUserId()) && appointment.isBooked();
    }

    public boolean hasBookingConflict(String appointmentDate, String startTime, String endTime, List<Appointment> appointments) {
        if (appointments == null) {
            return false;
        }
        for (Appointment appointment : appointments) {
            if (canTrackAppointment(appointment) && appointment.matchesTimeBlock(appointmentDate, startTime, endTime)) {
                return true;
            }
        }
        return false;
    }
}
