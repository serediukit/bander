package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Message {
    private String chatUID;
    private String senderType;
    private String message;
    private String datetime;
    private String status;

    public Message(
            String chatUID,
            String senderType,
            String message,
            String datetime,
            String status) {
        this.chatUID = chatUID;
        this.senderType = senderType;
        this.message = message;
        this.datetime = datetime;
        this.status = status;
    }

    public Message(Message message) {
        this.chatUID = message.chatUID;
        this.senderType = message.senderType;
        this.message = message.message;
        this.datetime = message.datetime;
        this.status = message.status;
    }

    public Message() {
        clear();
    }

    public String getChatUID() {
        return chatUID;
    }

    public String getSenderType() {
        return senderType;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getStatus() {
        return status;
    }

    private void clear() {
        this.chatUID = null;
        this.senderType = null;
        this.message = null;
        this.datetime = null;
        this.status = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nchatUID: " + chatUID +
                "\nsenderType: " + senderType +
                "\nmessage: " + message +
                "\ndatetime: " + datetime +
                "\nstatus: " + status +
                "\n}";
    }
}
