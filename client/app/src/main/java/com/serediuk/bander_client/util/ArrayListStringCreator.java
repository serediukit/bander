package com.serediuk.bander_client.util;

import com.serediuk.bander_client.model.entity.Message;

import java.util.ArrayList;

public class ArrayListStringCreator {
    public static String getStringFromArrayList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String getStringFromArrayListMessages(ArrayList<Message> list) {
        StringBuilder sb = new StringBuilder();
        for (Message m : list) {
            sb.append(m.toString());
            sb.append(" ");
        }
        return sb.toString();
    }
}
