package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

public class DonationRequestRest {
  private String requestId;
  private UserRest requestor;
  private String phoneNo;
  private double amount;
  private Date creationTime;
  private String description;
  private String disease;
  private VerificationStatus requestStatus;
  private CompletionStatus completionStatus;

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

  public String getDisease() {
    return disease;
  }

  public void setDisease(String disease) {
    this.disease = disease;
  }

  public VerificationStatus getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(VerificationStatus requestStatus) {
    this.requestStatus = requestStatus;
  }

  public CompletionStatus getCompletionStatus() {
    return completionStatus;
  }

  public void setCompletionStatus(CompletionStatus completionStatus) {
    this.completionStatus = completionStatus;
  }
}
