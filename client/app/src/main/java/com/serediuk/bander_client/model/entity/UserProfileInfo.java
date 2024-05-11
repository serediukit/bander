package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserProfileInfo {
    private String uid;
    private String about;
    private ArrayList<String> roles;
    private ArrayList<String> links;
    private String imageName;

    public UserProfileInfo(String uid, String about, ArrayList<String> roles, ArrayList<String> links, String imageName) {
        this.uid = uid;
        this.about = about;
        this.roles = roles;
        this.links = links;
        this.imageName = imageName;
    }

    public UserProfileInfo(UserProfileInfo userProfileInfo) {
        this.uid = userProfileInfo.uid;
        this.about = userProfileInfo.about;
        this.roles = userProfileInfo.roles;
        this.links = userProfileInfo.links;
        this.imageName = userProfileInfo.imageName;
    }

    public UserProfileInfo() {
        clear();
    }

    public String getUid() {
        return uid;
    }

    public String getAbout() {
        return about;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public String getImageName() {
        return imageName;
    }

    public void clear() {
        this.uid = "Empty";
        this.about = "Empty";
        this.roles = new ArrayList<>();
        roles.add("Empty");
        this.links = new ArrayList<>();
        roles.add("Empty");
        this.imageName = "Empty";
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nuid" + uid +
                "\nabout" + about +
                "\nlinks" + links +
                "\nroles" + roles +
                "\nimageName" + imageName +
                "\n}";
    }
}
