package com.lpnu.ecoplatformserver.exception;

public class DuplicatedEntryException extends RuntimeException {
    public DuplicatedEntryException(String message, Object... args) {
        super(String.format(message, args));
    }
}
