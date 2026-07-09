package com.tk.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public class GlobalExceptionHandler {

    public ResponseEntity<?> handleAlreadyExists(ResourceAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "message",ex.getMessage()
                ));
    }
}
