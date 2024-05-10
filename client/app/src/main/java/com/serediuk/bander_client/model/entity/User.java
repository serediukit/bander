package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class User {
    private final String uid;
    private final String name;
    private final String surname;
    private final String birthday;
    private final String city;

    public User(String uid, String name, String surname, String birthday, String city) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
    }

    public User(User user) {
        this.uid = user.uid;
        this.name = user.name;
        this.surname = user.surname;
        this.birthday = user.birthday;
        this.city = user.city;
    }

    public String getUid() {
        return uid;
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

    @NonNull
    @Override
    public String toString() {
        return "{\nname: " + name + "\nsurname: " + surname + "\nbirthday: " + birthday + "\ncity: " + city + "\n}";
    }
}
