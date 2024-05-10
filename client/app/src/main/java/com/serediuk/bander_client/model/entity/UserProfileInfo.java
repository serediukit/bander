package com.serediuk.bander_client.model.entity;

import java.util.ArrayList;

public class UserProfileInfo {
    private String uid;
    private String about;
    private ArrayList<String> roles;
    private ArrayList<String> links;

    public UserProfileInfo(String uid, String about, ArrayList<String> roles, ArrayList<String> links) {
        this.uid = uid;
        this.about = about;
        this.roles = roles;
        this.links = links;
    }

    public UserProfileInfo(UserProfileInfo userProfileInfo) {
        this.uid = userProfileInfo.uid;
        this.about = userProfileInfo.about;
        this.roles = userProfileInfo.roles;
        this.links = userProfileInfo.links;
    }

    public UserProfileInfo() {
        clear();
    }

    public void clear() {
        this.uid = "Empty";
        this.about = "Empty";
        this.roles = new ArrayList<>();
        roles.add("Empty");
        this.links = new ArrayList<>();
        roles.add("Empty");
    }
}
