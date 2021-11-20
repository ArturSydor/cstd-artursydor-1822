package com.lpnu.ecoplatformserver.config.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> defaultHandler(Exception exception) {
        return ResponseEntity
                .internalServerError()
                .body(exception.getMessage());
    }

}
