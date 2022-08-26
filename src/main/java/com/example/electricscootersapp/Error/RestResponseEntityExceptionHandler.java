package com.example.electricscootersapp.Error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<String> StartNotFoundException(StartNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> LocationNotFoundException(LocationNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> ScooterNotFoundException(ScooterNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> CustomerNotFoundException(CustomerNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> RentalAlreadyStartedException(RentalAlreadyStartedException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
