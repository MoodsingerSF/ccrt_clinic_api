package com.moodsinger.ccrt_clinic.model.request;

import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

public class DonationUpdateRequestModel {
  private VerificationStatus requestStatus;
  private CompletionStatus completionStatus;
  private String description;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
