package com.moodsinger.ccrt_clinic.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.moodsinger.ccrt_clinic.io.enums.Gender;

public class UserSignupRequestModel {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String userType;
  private Gender gender;
  private String specialization;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;
  private double fee;

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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }
}
