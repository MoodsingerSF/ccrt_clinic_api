package com.moodsinger.ccrt_clinic.model.request;

public class FeeChangingRequestModel {
  private double amount;
  private double previousAmount;
  private String userId;

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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
