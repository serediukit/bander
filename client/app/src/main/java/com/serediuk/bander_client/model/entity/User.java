package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class User {
    private String uid;
    private String email;
    private String name;
    private String city;
    private String type;

    public User(String uid, String email, String name, String city, String type) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.city = city;
        this.type = type;
    }

    public String getUid() {
        return uid;
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

    public String getType() {
        return type;
    }

    public void setUserData(User user) {
        this.uid = user.uid;
        this.email = user.email;
        this.name = user.name;
        this.city = user.city;
        this.type = user.type;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nuid: " + uid +
                "\nemail: " + email +
                "\nname: " + name +
                "\ncity: " + city +
                "\ntype:" + type +
                "\n}";
    }
}
