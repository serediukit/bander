package com.serediuk.bander_client.util.exceptions;

public class AuthException extends Exception {
    public AuthException() {
        super("Illegal credentials. Password should be at least 8 characters long, contains at least 1 character and 1 digit");
    }

    public AuthException(String errorMessage) {
        super(errorMessage);
    }
}
