package com.moodsinger.ccrt_clinic.model.request;

public class DonationRequestCreationRequestModel {
  private String requestorUserId;
  private String phoneNo;
  private double amount;
  private String description;
  private String disease;

  public String getRequestorUserId() {
    return requestorUserId;
  }

  public void setRequestorUserId(String requestorUserId) {
    this.requestorUserId = requestorUserId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisease() {
    return disease;
  }

  public void setDisease(String disease) {
    this.disease = disease;
  }
}
