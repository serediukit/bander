package com.serediuk.bander_client.util.string;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        Log.d("Validator", "Valid email: " + matcher.matches());
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        Log.d("Validator", "Valid password: " + matcher.matches());
        return matcher.matches();
    }
}
