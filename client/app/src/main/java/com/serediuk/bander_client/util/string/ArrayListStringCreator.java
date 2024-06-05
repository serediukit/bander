package com.serediuk.bander_client.util.string;

import com.serediuk.bander_client.model.entity.Message;

import java.util.ArrayList;

public class ArrayListStringCreator {
    public static String getStringFromArrayList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            if (list.indexOf(s) < list.size() - 1)
                sb.append(" ");
        }
        return sb.toString();
    }

    public static String getStringsFromArrayList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            if (list.indexOf(s) < list.size() - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    public static String getStringFromArrayListMessages(ArrayList<Message> list) {
        StringBuilder sb = new StringBuilder();
        for (Message m : list) {
            sb.append(m.toString());
            if (list.indexOf(m) < list.size() - 1)
                sb.append(" ");
        }
        return sb.toString();
    }
}
