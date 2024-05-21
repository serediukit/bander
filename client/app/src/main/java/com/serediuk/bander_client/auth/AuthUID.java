package com.serediuk.bander_client.auth;

public class AuthUID {
    public static String getUID() {
        return AuthProvider.getInstance().getUid();
    }
}
