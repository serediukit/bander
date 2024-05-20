package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Message {
    private String senderUID;
    private String message;
    private String datetime;

    public Message(String senderUID,
                   String message,
                   String datetime) {
        this.senderUID = senderUID;
        this.message = message;
        this.datetime = datetime;
    }

    public Message(Message message) {
        this.senderUID = message.senderUID;
        this.message = message.message;
        this.datetime = message.datetime;
    }

    public Message() {
        clear();
    }

    public String getSenderUID() {
        return senderUID;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }

    private void clear() {
        this.senderUID = null;
        this.message = null;
        this.datetime = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nsenderUID: " + senderUID +
                "\nmessage: " + message +
                "\ndatetime: " + datetime +
                "\n}";
    }
}
