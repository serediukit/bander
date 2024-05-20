package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

import com.serediuk.bander_client.auth.AuthProvider;
import com.serediuk.bander_client.util.ArrayListStringCreator;

import java.util.ArrayList;

public class Band {
    private String bandUID;
    private String email;
    private String name;
    private String city;
    private String genres;
    private ArrayList<String> membersID;

    public Band(String bandUID,
                String email,
                String name,
                String city,
                String genres,
                ArrayList<String> membersID) {
        this.bandUID = bandUID;
        this.email = email;
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        this.genres = genres;
        this.membersID = new ArrayList<>(membersID);
    }

    public Band(Band band) {
        this.bandUID = band.bandUID;
        this.email = band.email;
        this.name = band.name;
        this.city = band.city;
        this.genres = band.genres;
        this.membersID = new ArrayList<>(band.membersID);
    }

    public Band() {
        clear();
    }

    public String getBandUID() {
        return bandUID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getGenres() {
        return genres;
    }

    public ArrayList<String> getMembersID() {
        return new ArrayList<>(membersID);
    }

    public User getUser() {
        return new User(bandUID, email, name, city, "band");
    }

    private void clear() {
        this.bandUID = null;
        this.email = null;
        this.name = null;
        this.city = null;
        this.genres = null;
        this.membersID = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nbandUID: " + bandUID +
                "\nemail: " + email +
                "\nname: " + name +
                "\ncity: " + city +
                "\ngenres: " + genres +
                "\nmembers: " + ArrayListStringCreator.getStringFromArrayList(membersID) +
                "\n}";
    }
}
