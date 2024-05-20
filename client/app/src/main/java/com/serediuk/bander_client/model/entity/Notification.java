package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

import org.checkerframework.checker.units.qual.N;

public class Notification {
    private String notificationUID;
    private String receiverUID;
    private String title;
    private String text;
    private String datetime;

    public Notification(String notificationUID,
                        String receiverUID,
                        String title,
                        String text,
                        String datetime) {
        this.notificationUID = notificationUID;
        this.receiverUID = receiverUID;
        this.title = title;
        this.text = text;
        this.datetime = datetime;
    }

    public Notification(Notification notification) {
        this.notificationUID = notification.notificationUID;
        this.receiverUID = notification.receiverUID;
        this.title = notification.title;
        this.text = notification.text;
        this.datetime = notification.datetime;
    }

    public Notification() {
        clear();
    }

    public String getNotificationUID() {
        return notificationUID;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDatetime() {
        return datetime;
    }

    private void clear() {
        this.notificationUID = null;
        this.receiverUID = null;
        this.title = null;
        this.text = null;
        this.datetime = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nnotificationUID: " + notificationUID +
                "\nreceiverUID: " + receiverUID +
                "\ntitle: " + title +
                "\ntext: " + text +
                "\ndatetime: " + datetime +
                "\n}";
    }
}
