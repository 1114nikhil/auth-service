package com.tk.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExists(ResourceAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "message",ex.getMessage()
                ));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handelNotFound(ResourceNotFoundException ex){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "message",ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handelNotFound(Exception ex){
        return  ResponseEntity.internalServerError()
                .body(Map.of(
                        "timestamp",LocalDateTime.now(),
                        "message",ex.getMessage()
                ));
    }
}
