package com.example.mindmatters.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String type;
    private String bio;
    private String speciality;
    private int yearsExperience;
    private String profileImageUrl;
    private boolean supportsOnline;
    private boolean supportsInPerson;
    private List<AvailableSlot> availableSlots;

    public User() {
    }
    public User(String userId, String name, String email, String type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.type = type;
        this.bio = "";
        this.speciality = "General Support";
        this.yearsExperience = 0;
        this.profileImageUrl = "";
        this.supportsOnline = true;
        this.supportsInPerson = true;
        this.availableSlots = new ArrayList<>();
    }
    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getType() {
        return type;
    }
    public String getBio() {
        return bio == null || bio.isEmpty() ? "Bio coming soon." : bio;
    }
    public String getSpeciality() {
        return speciality == null || speciality.isEmpty() ? "General Support" : speciality;
    }
    public int getYearsExperience() {
        return yearsExperience;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public boolean isSupportsOnline() {
        return supportsOnline;
    }
    public boolean isSupportsInPerson() {
        return supportsInPerson;
    }
    public List<AvailableSlot> getAvailableSlots() {
        return availableSlots == null ? new ArrayList<>() : availableSlots;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public void setSupportsOnline(boolean supportsOnline) {
        this.supportsOnline = supportsOnline;
    }
    public void setSupportsInPerson(boolean supportsInPerson) {
        this.supportsInPerson = supportsInPerson;
    }
    public void setAvailableSlots(List<AvailableSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }
}
