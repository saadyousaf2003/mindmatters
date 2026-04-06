package com.example.mindmatters.classes;

import java.util.List;

/**
 * Role-specific user model that groups counsellor identity with profile and schedule collaborators.
 * Outstanding issues: counsellor-side editing workflows have not been built yet.
 */
public class Counsellor extends User {
    private CounsellorProfile profile;
    private CounsellorSchedule schedule;

    // Required empty constructor for Firestore object mapping.
    public Counsellor() {
        super();
        setType("counsellor");
        setDefaultValues();
    }

    // Creates a counsellor with base user information.
    public Counsellor(String userId, String name, String email) {
        super(userId, name, email, "counsellor");
        setDefaultValues();
    }

    // Initializes nested profile and schedule defaults.
    private void setDefaultValues() {
        profile = new CounsellorProfile();
        schedule = new CounsellorSchedule();
    }

    // Returns or creates the nested profile object.
    public CounsellorProfile getProfile() {
        if (profile == null) {
            profile = new CounsellorProfile();
        }
        return profile;
    }

    // Replaces the nested profile object.
    public void setProfile(CounsellorProfile profile) {
        this.profile = profile;
    }

    // Returns or creates the nested schedule object.
    public CounsellorSchedule getSchedule() {
        if (schedule == null) {
            schedule = new CounsellorSchedule();
        }
        return schedule;
    }

    // Replaces the nested schedule object.
    public void setSchedule(CounsellorSchedule schedule) {
        this.schedule = schedule;
    }

    // Convenience wrappers used by the current UI to reach profile and schedule data.
    public String getBio() {
        return getProfile().getBio();
    }

    public void setBio(String bio) {
        getProfile().setBio(bio);
    }

    public String getSpeciality() {
        return getProfile().getSpeciality();
    }

    public void setSpeciality(String speciality) {
        getProfile().setSpeciality(speciality);
    }

    public int getYearsExperience() {
        return getProfile().getYearsExperience();
    }

    public void setYearsExperience(int yearsExperience) {
        getProfile().setYearsExperience(yearsExperience);
    }

    public String getProfileImageUrl() {
        return getProfile().getProfileImageUrl();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        getProfile().setProfileImageUrl(profileImageUrl);
    }

    public boolean isSupportsOnline() {
        return getSchedule().isSupportsOnline();
    }

    public void setSupportsOnline(boolean supportsOnline) {
        getSchedule().setSupportsOnline(supportsOnline);
    }

    public boolean isSupportsInPerson() {
        return getSchedule().isSupportsInPerson();
    }

    public void setSupportsInPerson(boolean supportsInPerson) {
        getSchedule().setSupportsInPerson(supportsInPerson);
    }

    public List<AvailableSlot> getAvailableSlots() {
        return getSchedule().getAvailableSlots();
    }

    public void setAvailableSlots(List<AvailableSlot> availableSlots) {
        getSchedule().setAvailableSlots(availableSlots);
    }

    public boolean canAcceptBookings() {
        return getSchedule().canAcceptBookings();
    }

    public List<String> getAvailableMeetingModes() {
        return getSchedule().getAvailableMeetingModes();
    }

    public String getMeetingModesText() {
        return getSchedule().getMeetingModesText();
    }

    public String buildAvailableSlotsText() {
        return getSchedule().buildAvailableSlotsText();
    }
}
