package com.moodsinger.ccrt_clinic.model.request;

public class AppointmentCreationRequestModel {
  private String patientUserId;
  private String date;

  public String getPatientUserId() {
    return patientUserId;
  }

  public void setPatientUserId(String patientUserId) {
    this.patientUserId = patientUserId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
