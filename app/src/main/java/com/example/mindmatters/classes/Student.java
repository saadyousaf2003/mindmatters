package com.example.mindmatters.classes;

import java.util.List;

/**
 * Role-specific user model for student responsibilities such as browsing counsellors and tracking bookings.
 * Outstanding issues: most persistence and workflow orchestration still lives in Firebase-driven UI layers.
 */
public class Student extends User {
    // Required empty constructor for Firestore object mapping.
    public Student() {
        super();
        setType("student");
    }

    // Creates a student with base user information.
    public Student(String userId, String name, String email) {
        super(userId, name, email, "student");
    }

    // Returns whether students are allowed to browse counsellors.
    public boolean canViewCounsellors() {
        return true;
    }

    // Checks whether a counsellor is currently open for student bookings.
    public boolean canBookAppointmentWith(Counsellor counsellor) {
        return counsellor != null && counsellor.canAcceptBookings();
    }

    // Checks whether an appointment belongs in the student's active list.
    public boolean canTrackAppointment(Appointment appointment) {
        return appointment != null && appointment.belongsToStudent(getUserId()) && appointment.isBooked();
    }

    // Checks whether a proposed booking collides with an existing student appointment.
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
