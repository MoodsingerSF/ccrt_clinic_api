package com.moodsinger.ccrt_clinic.exceptions;

import org.springframework.http.HttpStatus;

public class UserServiceException extends RuntimeException {
  private String code;
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public UserServiceException(String message) {
    super(message);
  }

  public UserServiceException(String code, String message) {
    super(message);
    this.code = code;
  }

  public UserServiceException(String code, String message, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
