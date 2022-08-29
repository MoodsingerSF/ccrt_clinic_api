package com.moodsinger.ccrt_clinic.model.request;

public class SlotVisibilityUpdateRequestModel {
  @Override
  public String toString() {
    return "SlotVisibilityUpdateRequestModel [status=" + status + "]";
  }

  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
