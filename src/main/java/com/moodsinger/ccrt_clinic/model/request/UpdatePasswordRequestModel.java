package com.moodsinger.ccrt_clinic.model.request;

public class UpdatePasswordRequestModel {
  private String previousPassword;
  private String password;

  public String getPreviousPassword() {
    return previousPassword;
  }

  public void setPreviousPassword(String previousPassword) {
    this.previousPassword = previousPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
