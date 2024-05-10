package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class User {
    private String uid;
    private String email;
    private String name;
    private String surname;
    private String birthday;
    private String city;

    public User(String uid, String email, String name, String surname, String birthday, String city) {
        this.uid = uid;
        this.email = email;
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        this.surname = surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
        this.birthday = birthday;
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
    }

    public User(User user) {
        this.uid = user.uid;
        this.email = user.email;
        this.name = user.name.substring(0,1).toUpperCase() + user.name.substring(1).toLowerCase();
        this.surname = user.surname.substring(0,1).toUpperCase() + user.surname.substring(1).toLowerCase();
        this.birthday = user.birthday;
        this.city = user.city.substring(0,1).toUpperCase() + user.city.substring(1).toLowerCase();
    }

    public User() {
        clear();
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

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public void setUserData(User user) {
        this.uid = user.uid;
        this.email = user.email;
        this.name = user.name;
        this.surname = user.surname;
        this.birthday = user.birthday;
        this.city = user.city;
    }

    public void clear() {
        uid = "Empty";
        email = "Empty";
        name = "Empty";
        surname = "Empty";
        birthday = "Empty";
        city = "Empty";
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nuid: " + uid +
                "\nemail: " + email +
                "\nname: " + name +
                "\nsurname: " + surname +
                "\nbirthday: " + birthday +
                "\ncity: " + city +
                "\n}";
    }
}
