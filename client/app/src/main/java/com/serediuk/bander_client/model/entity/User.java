package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class User {
    private final String name;
    private final String surname;
    private final String birthDate;
    private final String city;

    public User(String name, String surname, String birthDate, String city) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCity() {
        return city;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nname: " + name + "\nsurname: " + surname + "\nbirthday: " + birthDate + "\ncity: " + city + "\n}";
    }
}
