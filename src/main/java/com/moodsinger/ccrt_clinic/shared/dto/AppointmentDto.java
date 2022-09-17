package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;

public class AppointmentDto {
  private String appointmentId;
  private String slotId;
  private String patientUserId;
  private String date;
  private FeeDto fee;
  private String meetingLink;
  private String patientVerificationCode;
  private SlotDto slot;
  private UserDto patient;
  private UserDto doctor;
  private AppointmentStatus status;
  private Date creationTime;
  private Date cancellationTime;
  private Date completionTime;

  public String getSlotId() {
    return slotId;
  }

  public void setSlotId(String slotId) {
    this.slotId = slotId;
  }

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

  public String getMeetingLink() {
    return meetingLink;
  }

  public void setMeetingLink(String meetingLink) {
    this.meetingLink = meetingLink;
  }

  public String getPatientVerificationCode() {
    return patientVerificationCode;
  }

  public void setPatientVerificationCode(String patientVerificationCode) {
    this.patientVerificationCode = patientVerificationCode;
  }

  public String getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(String appointmentId) {
    this.appointmentId = appointmentId;
  }

  public SlotDto getSlot() {
    return slot;
  }

  public void setSlot(SlotDto slot) {
    this.slot = slot;
  }

  public UserDto getPatient() {
    return patient;
  }

  public void setPatient(UserDto patient) {
    this.patient = patient;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getCancellationTime() {
    return cancellationTime;
  }

  public void setCancellationTime(Date cancellationTime) {
    this.cancellationTime = cancellationTime;
  }

  public Date getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(Date completionTime) {
    this.completionTime = completionTime;
  }

  public UserDto getDoctor() {
    return doctor;
  }

  public void setDoctor(UserDto doctor) {
    this.doctor = doctor;
  }

  public FeeDto getFee() {
    return fee;
  }

  public void setFee(FeeDto fee) {
    this.fee = fee;
  }

}
