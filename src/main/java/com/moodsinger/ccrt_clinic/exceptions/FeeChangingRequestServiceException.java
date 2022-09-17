package com.moodsinger.ccrt_clinic.exceptions;

import org.springframework.http.HttpStatus;

public class FeeChangingRequestServiceException extends RuntimeException {
  private String code;
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public FeeChangingRequestServiceException(String message) {
    super(message);
  }

  public FeeChangingRequestServiceException(String code, String message) {
    super(message);
    this.code = code;
  }

  public FeeChangingRequestServiceException(String code, String message, HttpStatus httpStatus) {
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
