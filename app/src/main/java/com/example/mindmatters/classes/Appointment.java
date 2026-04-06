package com.example.mindmatters.classes;

import com.google.firebase.Timestamp;

import java.io.Serializable;

/**
 * Domain model for a booked session between a student and a counsellor.
 * Outstanding issues: reschedule, cancellation history, and intake form linkage are not stored yet.
 */
public class Appointment implements Serializable {
    private String appointmentId;
    private String studentId;
    private String counsellorId;
    private String counsellorName;
    private String counsellorSpeciality;
    private String counsellorImageUrl;
    private String appointmentDate;
    private String startTime;
    private String endTime;
    private String meetingMode;
    private String status;
    private String studentLockId;
    private Timestamp createdAt;

    // Required empty constructor for Firestore object mapping.
    public Appointment() {
    }

    // Gets and updates the stored appointment fields used by Firestore and the student UI.
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(String counsellorId) {
        this.counsellorId = counsellorId;
    }

    public String getCounsellorName() {
        return counsellorName;
    }

    public void setCounsellorName(String counsellorName) {
        this.counsellorName = counsellorName;
    }

    public String getCounsellorSpeciality() {
        return counsellorSpeciality;
    }

    public void setCounsellorSpeciality(String counsellorSpeciality) {
        this.counsellorSpeciality = counsellorSpeciality;
    }

    public String getCounsellorImageUrl() {
        return counsellorImageUrl;
    }

    public void setCounsellorImageUrl(String counsellorImageUrl) {
        this.counsellorImageUrl = counsellorImageUrl;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
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

    public String getMeetingMode() {
        return meetingMode;
    }

    public void setMeetingMode(String meetingMode) {
        this.meetingMode = meetingMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentLockId() {
        return studentLockId;
    }

    public void setStudentLockId(String studentLockId) {
        this.studentLockId = studentLockId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Checks whether this appointment belongs to a given student.
    public boolean belongsToStudent(String studentId) {
        return studentId != null && studentId.equals(this.studentId);
    }

    // Checks whether this appointment uses the same date and time block.
    public boolean matchesTimeBlock(String appointmentDate, String startTime, String endTime) {
        return equalsValue(this.appointmentDate, appointmentDate)
                && equalsValue(this.startTime, startTime)
                && equalsValue(this.endTime, endTime);
    }

    // Returns whether the appointment is still in the booked state.
    public boolean isBooked() {
        return "BOOKED".equalsIgnoreCase(status);
    }

    // Compares two possibly null string values.
    private boolean equalsValue(String first, String second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }
}
