package com.moodsinger.ccrt_clinic.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moodsinger.ccrt_clinic.exceptions.model.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = { UserServiceException.class })
  public ResponseEntity<ErrorMessage> handleUserServiceException(UserServiceException exception) {
    ErrorMessage errorMessage = new ErrorMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { Exception.class })
  public ResponseEntity<ErrorMessage> handleUnhandledException(Exception exception) {
    exception.printStackTrace();
    ErrorMessage errorMessage = new ErrorMessage("INTERNAL_SERVER_ERROR", exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
