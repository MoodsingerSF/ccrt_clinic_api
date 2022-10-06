package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;

public class DonationRest {
  private String donationId;
  private UserRest donor;
  private double amount;
  private Date creationTime;
  private DonationRequestRest donationRequest;

  public String getDonationId() {
    return donationId;
  }

  public void setDonationId(String donationId) {
    this.donationId = donationId;
  }

  public UserRest getDonor() {
    return donor;
  }

  public void setDonor(UserRest donor) {
    this.donor = donor;
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

  public DonationRequestRest getDonationRequest() {
    return donationRequest;
  }

  public void setDonationRequest(DonationRequestRest donationRequest) {
    this.donationRequest = donationRequest;
  }
}
