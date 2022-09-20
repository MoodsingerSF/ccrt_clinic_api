package com.moodsinger.ccrt_clinic.io.entity;

public class PopularSpecialization {
  public PopularSpecialization(String name, long totalAppointments) {
    this.name = name;
    this.totalAppointments = totalAppointments;
  }

  public PopularSpecialization() {
  }

  private String name;
  private long totalAppointments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getTotalAppointments() {
    return totalAppointments;
  }

  public void setTotalAppointments(long totalAppointments) {
    this.totalAppointments = totalAppointments;
  }
}
