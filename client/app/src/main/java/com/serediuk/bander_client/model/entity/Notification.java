package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Notification {
    private String notificationUID;
    private String receiverUID;
    private String title;
    private String text;
    private String datetime;
    private String status;

    public Notification(String notificationUID,
                        String receiverUID,
                        String title,
                        String text,
                        String datetime,
                        String status) {
        this.notificationUID = notificationUID;
        this.receiverUID = receiverUID;
        this.title = title;
        this.text = text;
        this.datetime = datetime;
        this.status = status;
    }

    public Notification(Notification notification) {
        this.notificationUID = notification.notificationUID;
        this.receiverUID = notification.receiverUID;
        this.title = notification.title;
        this.text = notification.text;
        this.datetime = notification.datetime;
        this.status = notification.status;
    }

    public Notification() {
        clear();
    }

    public String getNotificationUID() {
        return notificationUID;
    }

    public void setNotificationUID(String notificationUID) {
        this.notificationUID = notificationUID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void clear() {
        this.notificationUID = null;
        this.receiverUID = null;
        this.title = null;
        this.text = null;
        this.datetime = null;
        this.status = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nnotificationUID: " + notificationUID +
                "\nreceiverUID: " + receiverUID +
                "\ntitle: " + title +
                "\ntext: " + text +
                "\ndatetime: " + datetime +
                "\nstatus: " + status +
                "\n}";
    }
}
