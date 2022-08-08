package com.moodsinger.ccrt_clinic.model.request;

public class OtpValidationRequestModel {
  private String otpId;
  private String code;

  public String getOtpId() {
    return this.otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
