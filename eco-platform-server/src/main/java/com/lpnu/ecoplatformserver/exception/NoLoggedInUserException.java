package com.lpnu.ecoplatformserver.exception;

public class NoLoggedInUserException extends RuntimeException {
    public NoLoggedInUserException(String message) {
        super(message);
    }
}
