package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Vacancy {
    private String vacancyUID;
    private String bandUID;
    private String role;
    private String text;
    private String salary;
    private String datetime;

    public Vacancy(String vacancyUID,
                   String bandUID,
                   String role,
                   String text,
                   String salary,
                   String datetime) {
        this.vacancyUID = vacancyUID;
        this.bandUID = bandUID;
        this.role = role;
        this.text = text;
        this.salary = salary;
        this.datetime = datetime;
    }

    public Vacancy(Vacancy vacancy) {
        this.vacancyUID = vacancy.vacancyUID;
        this.bandUID = vacancy.bandUID;
        this.role = vacancy.role;
        this.text = vacancy.text;
        this.salary = vacancy.salary;
        this.datetime = vacancy.datetime;
    }

    public Vacancy() {
        clear();
    }

    public String getVacancyUID() {
        return vacancyUID;
    }

    public void setVacancyUID(String vacancyUID) {
        this.vacancyUID = vacancyUID;
    }

    public String getBandUID() {
        return bandUID;
    }

    public String getRole() {
        return role;
    }

    public String getText() {
        return text;
    }

    public String getSalary() {
        return salary;
    }

    public String getDatetime() {
        return datetime;
    }

    private void clear() {
        this.vacancyUID = null;
        this.bandUID = null;
        this.role = null;
        this.text = null;
        this.salary = null;
        this.datetime = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nvacancyUID: " + vacancyUID +
                "\nbandUID: " + bandUID +
                "\nrole: " + role +
                "\ntext: " + text +
                "\nsalary: " + salary +
                "\ndatetime: " + datetime +
                "\n}";
    }
}
