package com.lpnu.ecoplatformserver.exception;

public class PublishedEntityModificationException extends RuntimeException {
    public PublishedEntityModificationException(String message, Object... args) {
        super(String.format(message, args));
    }
}
