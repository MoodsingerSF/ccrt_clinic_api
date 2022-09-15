package com.moodsinger.ccrt_clinic.model.request;

import java.util.List;

public class PrescriptionCreationRequestModel {
  private String advice;
  private List<MedicationCreationRequestModel> medications;

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public List<MedicationCreationRequestModel> getMedications() {
    return medications;
  }

  public void setMedications(List<MedicationCreationRequestModel> medications) {
    this.medications = medications;
  }

}
