package com.example.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerResourceNotFound(ResourceNotFoundException ex){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , HttpStatus.NOT_FOUND.value());
        body.put("error" , "resource not found");
        body.put("message" , ex.getMessage());
        return new ResponseEntity<>(body , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException ex){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , HttpStatus.BAD_REQUEST.value());
        body.put("error" , "incorrectly submitted request");
        body.put("message" , ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handlerEmailAlreadyExists(EmailAlreadyExistsException ex){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , HttpStatus.BAD_REQUEST.value());
        body.put("error" , "email already exists");
        body.put("message" , ex.getMessage());
        return new ResponseEntity<>(body , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IsbnAlreadyExistsException.class)
    public ResponseEntity<Object> handlerIsbnAlreadyExists(IsbnAlreadyExistsException ex){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , HttpStatus.BAD_REQUEST.value());
        body.put("error" , "isbn already exists");
        body.put("message" , ex.getMessage());
        return new ResponseEntity<>(body , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerGenericException(Exception ex){
        Map<String , Object> body = new LinkedHashMap<>();
        body.put("timestamp" , LocalDateTime.now());
        body.put("status" , HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error" , "internal server error");
        body.put("message" , ex.getMessage());
        return new ResponseEntity<>(body , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
