package com.moodsinger.ccrt_clinic.exceptions.enums;

public enum Messages {
  USER_NOT_FOUND("User is not found."),
  USERNAME_PASSWORD_MISMATCH("Username and password didn't match."),
  FIRST_NAME_NOT_VALID("First name is not valid."), LAST_NAME_NOT_VALID("Last name is not valid."),
  EMAIL_NOT_VALID("Email is not valid."), PASSWORD_NOT_VALID("password is not valid."),
  USER_NOT_CREATED("User isn't created."), PASSWORD_MISMATCH("Your current password haven't matched."),
  GENDER_NOT_VALID("Gender is not valid. Please provide a valid gender(MALE | FEMALE | OTHER)"),
  BIRTH_DATE_NOT_VALID("Birth date is not valid. Please provide a valid birth date."),
  SPECIALIZATION_NOT_VALID("Specialization can not be null for doctors."),
  FORBIDDEN("You don't have the permission to perform this operation."),
  REQUIRED_REQUEST_BODY("Request body is required."),
  MALFORMED_JSON_BODY("Request is not well formed."),
  FILE_SAVE_ERROR("The file couldn't be saved."),
  OTP_CODE_EXPIRED("Otp code has expired."),
  OTP_CODE_MISMATCH("Otp code hasn't matched."),
  PASSWORD_RESET_TOKEN_MISMATCH("Password reset token hasn't matched."),
  PASSWORD_UPDATE_SUCCESSFUL("Password has been updated successfully."),
  PASSWORD_RESET_SUCCESSFUL("Password has been reset successfully."),
  DONATION_REQUEST_NOT_FOUND("Donation request couldn't be found"),
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
  APPOINTMENT_NOT_FOUND("Appointment couldn't be found. It may either be deleted or does not exist."),
  RESOURCE_NOT_FOUND("Resource Couldn't be found"),
  EDUCATION_NOT_FOUND("Education entity couldn't be found."),
  TRAINING_NOT_FOUND("Training entity couldn't be found."),
  EXPERIENCE_NOT_FOUND("Experience entity couldn't be found."),
  AWARD_NOT_FOUND("Award entity couldn't be found."),
  FEE_FIELD_ERROR("Fee must be greater than 0."),
  REQUEST_NOT_FOUND("Request couldn't be found."),
  SLOT_BOOKED("Slot has already been booked."),
  FEE_CHANGING_REQUEST_CREATION_ERROR(
      "You already have a pending request. You can request another one once the pending request is resolved."),
  PRESCRIPTION_NOT_FOUND("Appointment has no prescription."),
  AMOUNT_ERROR("Amount must be greater than zero."),
  PRESCRIPTION_ALREADY_EXISTS(
      "Doctor has already added a prescription. You can not add another one. Try modifying the previous one."),
  APPOINTMENT_VERIFICATION_CODE_MISMATCH(
      "Appointment verification code didn't match. Please provide the correct code. If you don't have the code, please ask your patient for the code after the appointment finishes."),
  USER_TYPE_NOT_VALID("User type is not valid.");

  private String message;

  Messages(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
