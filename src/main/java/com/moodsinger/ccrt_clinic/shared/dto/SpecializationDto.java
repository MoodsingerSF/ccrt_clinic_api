package com.moodsinger.ccrt_clinic.shared.dto;

public class SpecializationDto {
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
