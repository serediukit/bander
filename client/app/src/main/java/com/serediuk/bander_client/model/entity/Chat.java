package com.serediuk.bander_client.model.entity;

import androidx.annotation.NonNull;

import com.serediuk.bander_client.model.enums.MessageStatus;
import com.serediuk.bander_client.util.string.ArrayListStringCreator;

import org.checkerframework.framework.qual.IgnoreInWholeProgramInference;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private String chatUID;
    private String candidateMemberUID;
    private String bandMemberUID;
    private ArrayList<Message> messages;

    public Chat(String chatUID,
                String candidateMemberUID,
                String bandMemberUID,
                ArrayList<Message> messages) {
        this.chatUID = chatUID;
        this.candidateMemberUID = candidateMemberUID;
        this.bandMemberUID = bandMemberUID;
        this.messages = new ArrayList<>(messages);
    }

    public Chat(Chat chat) {
        this.chatUID = chat.chatUID;
        this.candidateMemberUID = chat.candidateMemberUID;
        this.bandMemberUID = chat.bandMemberUID;
        this.messages = new ArrayList<>(chat.messages);
    }

    public Chat() {
        clear();
    }

    public String getChatUID() {
        return chatUID;
    }

    public void setChatUID(String chatUID) {
        this.chatUID = chatUID;
    }

    public String getCandidateMemberUID() {
        return candidateMemberUID;
    }

    public String getBandMemberUID() {
        return bandMemberUID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage() {
        Message res = null;
        for (Message message : messages) {
            if (res == null) {
                res = message;
            } else {
                if (message.getDatetime().compareTo(res.getDatetime()) > 0) {
                    res = message;
                }
            }
        }
        return res;
    }

    public int getNewMessagesCount() {
        int res = 0;
        for (Message message : messages) {
            if (message.getStatus().equals(MessageStatus.SENT.toString())) {
                res++;
            }
        }
        return res;
    }

    private void clear() {
        this.chatUID = null;
        this.candidateMemberUID = null;
        this.bandMemberUID = null;
        this.messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nchatUID: " + chatUID +
                "\ncandidateMemberUID: " + candidateMemberUID +
                "\nbandMemberUID: " + bandMemberUID +
                "\nmessages: " + ArrayListStringCreator.getStringFromArrayListMessages(messages) +
                "\n}";
    }
}
