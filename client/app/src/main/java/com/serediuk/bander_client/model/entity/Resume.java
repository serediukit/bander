package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Resume {
    private String resumeUID;
    private String candidateUID;
    private String vacancyUID;
    private String text;
    private String datetime;

    public Resume(String resumeUID,
                  String candidateUID,
                  String vacancyUID,
                  String text,
                  String datetime) {
        this.resumeUID = resumeUID;
        this.candidateUID = candidateUID;
        this.vacancyUID = vacancyUID;
        this.text = text;
        this.datetime = datetime;
    }

    public String getResumeUID() {
        return resumeUID;
    }

    public String getCandidateUID() {
        return candidateUID;
    }

    public String getVacancyUID() {
        return vacancyUID;
    }

    public String getText() {
        return text;
    }

    public String getDatetime() {
        return datetime;
    }

    private void clear() {
        this.resumeUID = null;
        this.candidateUID = null;
        this.vacancyUID = null;
        this.text = null;
        this.datetime = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nresumeUID: " + resumeUID +
                "\ncandidateUID: " + candidateUID +
                "\nvacancyUID: " + vacancyUID +
                "\ntext: " + text +
                "\ndatetime: " + datetime +
                "\n}";
    }
}
