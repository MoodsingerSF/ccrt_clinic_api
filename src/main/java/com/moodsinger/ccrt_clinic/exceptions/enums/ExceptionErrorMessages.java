package com.moodsinger.ccrt_clinic.exceptions.enums;

public enum ExceptionErrorMessages {
  USER_NOT_FOUND("User is not found."),
  USERNAME_PASSWORD_MISMATCH("Username and password didn't match."),
  FIRST_NAME_NOT_VALID("First name is not valid."), LAST_NAME_NOT_VALID("Last name is not valid."),
  EMAIL_NOT_VALID("Email is not valid."), PASSWORD_NOT_VALID("password is not valid."),
  USER_NOT_CREATED("User isn't created."),
  FORBIDDEN("You don't have the permission to perform this operation."),
  REQUIRED_REQUEST_BODY("Request body is required."),
  MALFORMED_JSON_BODY("Request is not well formed."),
  FILE_SAVE_ERROR("The file couldn't be saved."),
  OTP_CODE_EXPIRED("Otp code has expired."),
  OTP_CODE_MISMATCH("Otp code hasn't matched."),
  BLOG_NOT_FOUND(
      "The blog you are looking for is not available. It may have been removed or you are looking for an invalid blog."),
  USER_OTP_SERVICE_BLOCKED(
      "Too many requests within a short amount of time. User is blocked temporarily. Please try again later."),
  DOCTOR_SCHEDULE_CREATION_ERROR("Doctor schedule was not initialized properly."),
  DOCTOR_REQUEST_PENDING_APPROVAL(
      "Your request to sign up as a doctor is currently in review. You will be notified via email after the verification of your identity."),
  SLOT_NOT_CREATED("Slot couldn't be created"),
  SCHEDULE_NOT_FOUND("Schedule couldn't be found. Please sign up as a doctor."),
  OVERLAPPING_SLOT("Overlapping slot is found."),
  SLOT_NOT_FOUND("Slot couldn't be found."),
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
