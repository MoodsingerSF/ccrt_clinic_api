package com.moodsinger.ccrt_clinic.exceptions.enums;

public enum ExceptionErrorMessages {
  USER_NOT_FOUND("User is not found."),
  USERNAME_PASSWORD_MISMATCH("Username and password didn't match."),
  FIRST_NAME_NOT_VALID("First name is not valid."), LAST_NAME_NOT_VALID("Last name is not valid."),
  EMAIL_NOT_VALID("Email is not valid."), PASSWORD_NOT_VALID("password is not valid."),
  USER_NOT_CREATED("User isn't created."),
  FORBIDDEN("User doesn't have the permission to perform this operation."),
  REQUIRED_REQUEST_BODY("Request body is required."),
  MALFORMED_JSON_BODY("Request is not well formed."),
  USER_TYPE_NOT_VALID("User type is not valid.");

  private String message;

  ExceptionErrorMessages(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
