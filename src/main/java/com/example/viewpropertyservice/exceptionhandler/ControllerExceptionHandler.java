package com.example.viewpropertyservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = PropertyNotFoundException.class)
  public ResponseEntity<Object> propertyNotFoundException(PropertyNotFoundException exception) {
    return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
  }

}
