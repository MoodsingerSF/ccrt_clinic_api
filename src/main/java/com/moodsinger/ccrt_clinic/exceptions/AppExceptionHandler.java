package com.moodsinger.ccrt_clinic.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moodsinger.ccrt_clinic.exceptions.model.ResponseMessage;

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
  public ResponseEntity<ResponseMessage> handleUserServiceException(UserServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { DonationRequestServiceException.class })
  public ResponseEntity<ResponseMessage> handleDonationRequestServiceException(
      DonationRequestServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { AppointmentServiceException.class })
  public ResponseEntity<ResponseMessage> handleAppointmentServiceException(AppointmentServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { BlogServiceException.class })
  public ResponseEntity<ResponseMessage> handleBlogServiceException(BlogServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { OtpServiceException.class })
  public ResponseEntity<ResponseMessage> handleOtpServiceException(OtpServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { FeeChangingRequestServiceException.class })
  public ResponseEntity<ResponseMessage> handleFeeChangingRequestServiceException(
      FeeChangingRequestServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { DoctorScheduleServiceException.class })
  public ResponseEntity<ResponseMessage> handleDoctorScheduleServiceException(
      DoctorScheduleServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage(exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { Exception.class })
  public ResponseEntity<ResponseMessage> handleUnhandledException(Exception exception) {
    exception.printStackTrace();
    ResponseMessage errorMessage = new ResponseMessage("INTERNAL_SERVER_ERROR", exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
