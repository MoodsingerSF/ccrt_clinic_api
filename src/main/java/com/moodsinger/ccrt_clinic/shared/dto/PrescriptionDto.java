package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;
import java.util.List;

public class PrescriptionDto {
  private String prescriptionId;
  private String appointmentId;
  private String advice;
  private List<MedicationDto> medications;
  private Date creationTime;
  private Date date;
  private UserDto doctor;
  private UserDto patient;

  public String getPrescriptionId() {
    return prescriptionId;
  }

  public void setPrescriptionId(String prescriptionId) {
    this.prescriptionId = prescriptionId;
  }

  public String getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(String appointmentId) {
    this.appointmentId = appointmentId;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public UserDto getDoctor() {
    return doctor;
  }

  public void setDoctor(UserDto doctor) {
    this.doctor = doctor;
  }

  public UserDto getPatient() {
    return patient;
  }

  public void setPatient(UserDto patient) {
    this.patient = patient;
  }

  public List<MedicationDto> getMedications() {
    return medications;
  }

  public void setMedications(List<MedicationDto> medications) {
    this.medications = medications;
  }
}
