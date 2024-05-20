package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

public class Message {
    private String chatUID;
    private String senderUID;
    private String message;
    private String datetime;

    public Message(String chatUID,
                   String senderUID,
                   String message,
                   String datetime) {
        this.chatUID = chatUID;
        this.senderUID = senderUID;
        this.message = message;
        this.datetime = datetime;
    }

    public Message(Message message) {
        this.chatUID = message.chatUID;
        this.senderUID = message.senderUID;
        this.message = message.message;
        this.datetime = message.datetime;
    }

    public Message() {
        clear();
    }

    public String getChatUID() {
        return chatUID;
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
        this.chatUID = null;
        this.senderUID = null;
        this.message = null;
        this.datetime = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nchatUID: " + chatUID +
                "\nsenderUID: " + senderUID +
                "\nmessage: " + message +
                "\ndatetime: " + datetime +
                "\n}";
    }
}
