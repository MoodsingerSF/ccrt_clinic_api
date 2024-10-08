package com.moodsinger.ccrt_clinic.shared.dto;

public class FeeDto {
  public FeeDto(double amount, String userId) {
    this.amount = amount;
    this.userId = userId;
  }

  public FeeDto() {
  }

  private double amount;
  private String userId;

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
