package com.serediuk.bander_client.exceptions;

public class AuthException extends Exception {
    public AuthException() {
        super("Illegal credentials");
    }
}
