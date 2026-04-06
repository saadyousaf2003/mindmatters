package com.example.mindmatters.classes;

import java.io.Serializable;

/**
 * Profile model that holds student-facing counsellor biography and experience details.
 * Outstanding issues: optional fields like location, gender, and richer media are still missing.
 */
public class CounsellorProfile implements Serializable {
    private String bio;
    private String speciality;
    private int yearsExperience;
    private String profileImageUrl;

    // Creates default profile values for a counsellor.
    public CounsellorProfile() {
        bio = "";
        speciality = "General Support";
        yearsExperience = 0;
        profileImageUrl = "";
    }

    // Gets and updates student-facing counsellor profile fields.
    public String getBio() {
        return bio == null || bio.isEmpty() ? "Bio coming soon." : bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSpeciality() {
        return speciality == null || speciality.isEmpty() ? "General Support" : speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
