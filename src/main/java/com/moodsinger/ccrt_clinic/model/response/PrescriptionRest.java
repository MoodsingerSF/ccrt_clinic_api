package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;
import java.util.List;

public class PrescriptionRest {
  private UserRest doctor;
  private UserRest patient;
  private Date date;
  private String advice;
  private List<MedicationRest> medications;

  public UserRest getDoctor() {
    return doctor;
  }

  public void setDoctor(UserRest doctor) {
    this.doctor = doctor;
  }

  public UserRest getPatient() {
    return patient;
  }

  public void setPatient(UserRest patient) {
    this.patient = patient;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public List<MedicationRest> getMedications() {
    return medications;
  }

  public void setMedications(List<MedicationRest> medications) {
    this.medications = medications;
  }

}
