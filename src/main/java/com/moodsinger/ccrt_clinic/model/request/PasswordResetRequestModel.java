package com.moodsinger.ccrt_clinic.model.request;

public class PasswordResetRequestModel {
  private String resetPasswordToken;
  private String password;

  public String getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
