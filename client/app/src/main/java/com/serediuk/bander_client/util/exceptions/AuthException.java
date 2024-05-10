package com.serediuk.bander_client.util.exceptions;

public class AuthException extends Exception {
    public AuthException() {
        super("Illegal credentials");
    }

    public AuthException(String errorMessage) {
        super(errorMessage);
    }
}
