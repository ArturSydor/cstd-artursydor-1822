package com.lpnu.ecoplatformserver.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String message, Object... args) {
        super(String.format(message, args));
    }
}
