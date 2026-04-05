package com.example.mindmatters.classes;

import java.util.List;

public class Counsellor extends User {
    private CounsellorProfile profile;
    private CounsellorSchedule schedule;

    public Counsellor() {
        super();
        setType("counsellor");
        setDefaultValues();
    }

    public Counsellor(String userId, String name, String email) {
        super(userId, name, email, "counsellor");
        setDefaultValues();
    }

    private void setDefaultValues() {
        profile = new CounsellorProfile();
        schedule = new CounsellorSchedule();
    }

    public CounsellorProfile getProfile() {
        if (profile == null) {
            profile = new CounsellorProfile();
        }
        return profile;
    }

    public void setProfile(CounsellorProfile profile) {
        this.profile = profile;
    }

    public CounsellorSchedule getSchedule() {
        if (schedule == null) {
            schedule = new CounsellorSchedule();
        }
        return schedule;
    }

    public void setSchedule(CounsellorSchedule schedule) {
        this.schedule = schedule;
    }

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
