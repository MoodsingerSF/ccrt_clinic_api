package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

public class FeeChangingRequestRest {
  private String requestId;
  private UserRest user;
  private double amount;
  private double previousAmount;
  private Date creationTime;
  private VerificationStatus status;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public UserRest getUser() {
    return user;
  }

  public void setUser(UserRest user) {
    this.user = user;
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
}
