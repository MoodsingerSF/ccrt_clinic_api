package com.moodsinger.ccrt_clinic.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moodsinger.ccrt_clinic.exceptions.model.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

  // @ExceptionHandler(value = { DisabledException.class })
  // public ResponseEntity<ErrorMessage> handleDoctorScheduleServiceException(
  // DisabledException exception) {
  // ErrorMessage errorMessage = new ErrorMessage("REQUEST_PENDING_APPROVAL",
  // exception.getMessage());
  // return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(),
  // HttpStatus.LOCKED);
  // }

  @ExceptionHandler(value = { UserServiceException.class })
  public ResponseEntity<ErrorMessage> handleUserServiceException(UserServiceException exception) {
    ErrorMessage errorMessage = new ErrorMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { AppointmentServiceException.class })
  public ResponseEntity<ErrorMessage> handleAppointmentServiceException(AppointmentServiceException exception) {
    ErrorMessage errorMessage = new ErrorMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { BlogServiceException.class })
  public ResponseEntity<ErrorMessage> handleBlogServiceException(BlogServiceException exception) {
    ErrorMessage errorMessage = new ErrorMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { OtpServiceException.class })
  public ResponseEntity<ErrorMessage> handleOtpServiceException(OtpServiceException exception) {
    ErrorMessage errorMessage = new ErrorMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ErrorMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { DoctorScheduleServiceException.class })
  public ResponseEntity<ErrorMessage> handleDoctorScheduleServiceException(
      DoctorScheduleServiceException exception) {
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
