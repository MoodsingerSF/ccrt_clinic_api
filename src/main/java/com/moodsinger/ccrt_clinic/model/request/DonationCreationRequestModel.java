package com.moodsinger.ccrt_clinic.model.request;

public class DonationCreationRequestModel {
  private String donationRequestId;
  private String donorUserId;
  private double amount;

  public String getDonationRequestId() {
    return donationRequestId;
  }

  public void setDonationRequestId(String donationRequestId) {
    this.donationRequestId = donationRequestId;
  }

  public String getDonorUserId() {
    return donorUserId;
  }

  public void setDonorUserId(String donorUserId) {
    this.donorUserId = donorUserId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

}
