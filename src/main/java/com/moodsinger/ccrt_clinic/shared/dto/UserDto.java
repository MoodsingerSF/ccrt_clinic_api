package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Set;

public class UserDto {
  private long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private Set<RoleDto> roles;
  private String userType;
  private String role;
  private String verificationStatus;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public Set<RoleDto> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDto> roles) {
    this.roles = roles;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(String verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
