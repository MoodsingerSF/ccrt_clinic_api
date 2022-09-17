package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

public class FeeChangingRequestDto {
  private String requestId;
  private String userId;
  private double amount;
  private double previousAmount;
  private Date creationTime;
  private VerificationStatus status;
  private UserDto user;
  private String adminUserId;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getPreviousAmount() {
    return previousAmount;
  }

  public void setPreviousAmount(double previousAmount) {
    this.previousAmount = previousAmount;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public VerificationStatus getStatus() {
    return status;
  }

  public void setStatus(VerificationStatus status) {
    this.status = status;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public String getAdminUserId() {
    return adminUserId;
  }

  public void setAdminUserId(String adminUserId) {
    this.adminUserId = adminUserId;
  }
}
