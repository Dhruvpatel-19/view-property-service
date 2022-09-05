package com.example.viewpropertyservice.exception.handler;

import com.example.viewpropertyservice.exception.*;
import org.apache.http.HttpResponse;
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
  public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception){
    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = JwtTokenExpiredException.class)
  public ResponseEntity<Object> jwtTokenExpiredException(JwtTokenExpiredException exception){
    return new ResponseEntity<>("Jwt Token has been expired" , HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = JwtSignatureException.class)
  public ResponseEntity<Object> jwtSignatureException(JwtSignatureException exception){
    return new ResponseEntity<>("Jwt token is not properly formatted" , HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = PropertyAlreadyInFavouriteException.class)
  public ResponseEntity<Object> propertyAlreadyInFavouriteException(PropertyAlreadyInFavouriteException exception){
    return new ResponseEntity<>("Property is already in favourite list" , HttpStatus.ALREADY_REPORTED);
  }

  @ExceptionHandler(value = PropertyNotExistsInFavouriteException.class)
  public ResponseEntity<Object> propertyNotExistsInFavouriteException(PropertyNotExistsInFavouriteException exception){
    return new ResponseEntity<>("Property is not in user favourite list " , HttpStatus.NOT_FOUND);
  }


}
