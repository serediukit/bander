package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

import com.serediuk.bander_client.model.enums.UserType;
import com.serediuk.bander_client.util.string.ArrayListStringCreator;

import java.util.ArrayList;

public class Band {
    private String bandUID;
    private String email;
    private String name;
    private String city;
    private String genres;
    private String about;
    private String videoLinks;
    private ArrayList<String> membersID;

    public Band(String bandUID,
                String email,
                String name,
                String city,
                String genres,
                String about,
                String videoLinks,
                ArrayList<String> membersID) {
        this.bandUID = bandUID;
        this.email = email;
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        this.genres = genres;
        this.about = about;
        this.videoLinks = videoLinks;
        this.membersID = new ArrayList<>(membersID);
    }

    public Band(Band band) {
        this.bandUID = band.bandUID;
        this.email = band.email;
        this.name = band.name;
        this.city = band.city;
        this.genres = band.genres;
        this.about = band.about;
        this.videoLinks = band.videoLinks;
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

    public String getAbout() {
        return about;
    }

    public String getVideoLinks() {
        return videoLinks;
    }

    public ArrayList<String> getMembersID() {
        return new ArrayList<>(membersID);
    }

    public User getUser() {
        return new User(bandUID, email, name, city, UserType.BAND.toString());
    }

    private void clear() {
        this.bandUID = null;
        this.email = null;
        this.name = null;
        this.city = null;
        this.genres = null;
        this.about = null;
        this.videoLinks = null;
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
                "\nabout: " + about +
                "\nvideoLinks: " + videoLinks +
                "\nmembers: " + ArrayListStringCreator.getStringFromArrayList(membersID) +
                "\n}";
    }
}
