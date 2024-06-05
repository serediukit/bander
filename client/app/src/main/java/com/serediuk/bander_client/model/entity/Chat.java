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

    public Chat(String chatUID,
                String candidateMemberUID,
                String bandMemberUID) {
        this.chatUID = chatUID;
        this.candidateMemberUID = candidateMemberUID;
        this.bandMemberUID = bandMemberUID;
    }

    public Chat(Chat chat) {
        this.chatUID = chat.chatUID;
        this.candidateMemberUID = chat.candidateMemberUID;
        this.bandMemberUID = chat.bandMemberUID;
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

    private void clear() {
        this.chatUID = null;
        this.candidateMemberUID = null;
        this.bandMemberUID = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\nchatUID: " + chatUID +
                "\ncandidateMemberUID: " + candidateMemberUID +
                "\nbandMemberUID: " + bandMemberUID +
                "\n}";
    }
}
