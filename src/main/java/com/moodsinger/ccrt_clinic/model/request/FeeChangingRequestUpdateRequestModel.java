package com.moodsinger.ccrt_clinic.model.request;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

public class FeeChangingRequestUpdateRequestModel {
  private VerificationStatus status;
  private String adminUserId;

  public VerificationStatus getStatus() {
    return status;
  }

  public void setStatus(VerificationStatus status) {
    this.status = status;
  }

  public String getAdminUserId() {
    return adminUserId;
  }

  public void setAdminUserId(String adminUserId) {
    this.adminUserId = adminUserId;
  }
}
