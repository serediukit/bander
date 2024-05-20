package com.serediuk.bander_client.util;

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
}
