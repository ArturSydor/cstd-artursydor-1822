package com.lpnu.ecoplatformserver.exception;

public class NoActiveUserFound extends RuntimeException {
    public NoActiveUserFound(String message, Object... args) {
        super(String.format(message, args));
    }
}
