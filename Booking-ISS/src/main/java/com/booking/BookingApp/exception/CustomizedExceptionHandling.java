package com.booking.BookingApp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {


//    @ExceptionHandler(BadLogicException.class)
//    public ResponseEntity<Object> handleExceptions(BadLogicException exception, WebRequest webRequest) {
//        ExceptionResponse response = new ExceptionResponse();
//        response.setMessage(exception.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

}
