package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;

public class DonationRequestRest {
  private String requestId;
  private UserRest requestor;
  private String phoneNo;
  private double amount;
  private Date creationTime;
  private String description;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public UserRest getRequestor() {
    return requestor;
  }

  public void setRequestor(UserRest requestor) {
    this.requestor = requestor;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
