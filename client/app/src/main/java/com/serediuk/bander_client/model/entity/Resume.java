package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Resume {
    private String resumeUID;
    private String candidateUID;
    private String vacancyUID;
    private String text;
    private String datetime;
    private String status;

    public Resume(String resumeUID,
                  String candidateUID,
                  String vacancyUID,
                  String text,
                  String datetime,
                  String status) {
        this.resumeUID = resumeUID;
        this.candidateUID = candidateUID;
        this.vacancyUID = vacancyUID;
        this.text = text;
        this.datetime = datetime;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    private void clear() {
        this.resumeUID = null;
        this.candidateUID = null;
        this.vacancyUID = null;
        this.text = null;
        this.datetime = null;
        this.status = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nresumeUID: " + resumeUID +
                "\ncandidateUID: " + candidateUID +
                "\nvacancyUID: " + vacancyUID +
                "\ntext: " + text +
                "\ndatetime: " + datetime +
                "\nstatus: " + status +
                "\n}";
    }
}
