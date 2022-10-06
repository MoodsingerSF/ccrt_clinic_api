package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;

public class DonationDto {
  private String donationId;
  private UserDto donor;
  private double amount;
  private Date creationTime;
  private DonationRequestDto donationRequest;
  private String donationRequestId;
  private String donorUserId;

  public String getDonationId() {
    return donationId;
  }

  public void setDonationId(String donationId) {
    this.donationId = donationId;
  }

  public UserDto getDonor() {
    return donor;
  }

  public void setDonor(UserDto donor) {
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

  public DonationRequestDto getDonationRequest() {
    return donationRequest;
  }

  public void setDonationRequest(DonationRequestDto donationRequest) {
    this.donationRequest = donationRequest;
  }

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

}
