package com.lpnu.ecoplatformserver.exception;

public class ObjectAccessDeniedException extends RuntimeException{
    public ObjectAccessDeniedException(String message, Object... args) {
        super(String.format(message, args));
    }
}
