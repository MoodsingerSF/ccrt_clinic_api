package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;

public class AppointmentRest {
  private String appointmentId;
  private String meetingLink;
  private String date;
  private AppointmentStatus status;
  private Date creationTime;
  private Date completionTime;
  private Date cancellationTime;
  private SlotRest slot;
  private UserRest patient;
  private UserRest doctor = null;

  public String getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(String appointmentId) {
    this.appointmentId = appointmentId;
  }

  public String getMeetingLink() {
    return meetingLink;
  }

  public void setMeetingLink(String meetingLink) {
    this.meetingLink = meetingLink;
  }

  public SlotRest getSlot() {
    return slot;
  }

  public void setSlot(SlotRest slot) {
    this.slot = slot;
  }

  public UserRest getPatient() {
    return patient;
  }

  public void setPatient(UserRest patient) {
    this.patient = patient;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
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

  public Date getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(Date completionTime) {
    this.completionTime = completionTime;
  }

  public Date getCancellationTime() {
    return cancellationTime;
  }

  public void setCancellationTime(Date cancellationTime) {
    this.cancellationTime = cancellationTime;
  }

  public UserRest getDoctor() {
    return doctor;
  }

  public void setDoctor(UserRest doctor) {
    this.doctor = doctor;
  }

}
