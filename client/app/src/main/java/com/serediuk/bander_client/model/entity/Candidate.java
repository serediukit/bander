package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Candidate {
    private String candidateUID;
    private String email;
    private String name;
    private String surname;
    private String birthday;
    private String city;
    private String role;
    private String preferredGenres;
    private String experience;
    private String videoLinks;

    public Candidate(String candidateUID,
                     String email,
                     String name,
                     String surname,
                     String birthday,
                     String city,
                     String role,
                     String preferredGenres,
                     String experience,
                     String videoLinks) {
        this.candidateUID = candidateUID;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
        this.role = role;
        this.preferredGenres = preferredGenres;
        this.experience = experience;
        this.videoLinks = videoLinks;
    }

    public Candidate(Candidate candidate) {
        this.candidateUID = candidate.candidateUID;
        this.email = candidate.email;
        this.name = candidate.name;
        this.surname = candidate.surname;
        this.birthday = candidate.birthday;
        this.city = candidate.city;
        this.role = candidate.role;
        this.preferredGenres = candidate.preferredGenres;
        this.experience = candidate.experience;
        this.videoLinks = candidate.videoLinks;
    }

    public Candidate() {
        clear();
    }

    public String getCandidateUID() {
        return candidateUID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getRole() {
        return role;
    }

    public String getPreferredGenres() {
        return preferredGenres;
    }

    public String getExperience() {
        return experience;
    }

    public String getVideoLinks() {
        return videoLinks;
    }

    private void clear() {
        this.candidateUID = null;
        this.email = null;
        this.name = null;
        this.surname = null;
        this.birthday = null;
        this.city = null;
        this.role = null;
        this.preferredGenres = null;
        this.experience = null;
        this.videoLinks = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\ncandidateUID: " + candidateUID +
                "\nemail: " + email +
                "\nname: " + name +
                "\nsurname: " + surname +
                "\nbirthday: " + birthday +
                "\ncity: " + city +
                "\nrole: " + role +
                "\npreferredGenres: " + preferredGenres +
                "\nexperience: " + experience +
                "\nvideoLinks: " + videoLinks +
                "\n}";
    }
}
