package com.moodsinger.ccrt_clinic.io.entity;

public class DoctorAppointmentCount {

  public DoctorAppointmentCount(String userId, long totalAppointments) {
    this.userId = userId;
    this.totalAppointments = totalAppointments;
  }

  public DoctorAppointmentCount() {
  }

  private String userId;

  private long totalAppointments;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public long getTotalAppointments() {
    return totalAppointments;
  }

  public void setTotalAppointments(long totalAppointments) {
    this.totalAppointments = totalAppointments;
  }

}
