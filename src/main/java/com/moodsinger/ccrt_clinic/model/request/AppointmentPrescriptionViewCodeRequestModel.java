package com.moodsinger.ccrt_clinic.model.request;

public class AppointmentPrescriptionViewCodeRequestModel {
  private String codeForPrescriptionViewForPatient;
  private String userId;

  public String getCodeForPrescriptionViewForPatient() {
    return codeForPrescriptionViewForPatient;
  }

  public void setCodeForPrescriptionViewForPatient(String codeForPrescriptionViewForPatient) {
    this.codeForPrescriptionViewForPatient = codeForPrescriptionViewForPatient;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
