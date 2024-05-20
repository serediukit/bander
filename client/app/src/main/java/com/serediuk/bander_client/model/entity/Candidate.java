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
    private String preferred_genres;
    private String experience;
    private String video_links;

    public Candidate(String candidateUID,
                     String email,
                     String name,
                     String surname,
                     String birthday,
                     String city,
                     String role,
                     String preferred_genres,
                     String experience,
                     String video_links) {
        this.candidateUID = candidateUID;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
        this.role = role;
        this.preferred_genres = preferred_genres;
        this.experience = experience;
        this.video_links = video_links;
    }

    public Candidate(Candidate candidate) {
        this.candidateUID = candidate.candidateUID;
        this.email = candidate.email;
        this.name = candidate.name;
        this.surname = candidate.surname;
        this.birthday = candidate.birthday;
        this.city = candidate.city;
        this.role = candidate.role;
        this.preferred_genres = candidate.preferred_genres;
        this.experience = candidate.experience;
        this.video_links = candidate.video_links;
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

    public String getPreferred_genres() {
        return preferred_genres;
    }

    public String getExperience() {
        return experience;
    }

    public String getVideo_links() {
        return video_links;
    }

    private void clear() {
        this.candidateUID = null;
        this.email = null;
        this.name = null;
        this.surname = null;
        this.birthday = null;
        this.city = null;
        this.role = null;
        this.preferred_genres = null;
        this.experience = null;
        this.video_links = null;
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
                "\npreferred_genres: " + preferred_genres +
                "\nexperience: " + experience +
                "\nvideo_links: " + video_links +
                "\n}";
    }
}
