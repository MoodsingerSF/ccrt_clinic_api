package com.moodsinger.ccrt_clinic.model.request;

public class UserSignupRequestModel {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String userType;

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserSignupRequestModel [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
        + ", password=" + password + "]";
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }
}
