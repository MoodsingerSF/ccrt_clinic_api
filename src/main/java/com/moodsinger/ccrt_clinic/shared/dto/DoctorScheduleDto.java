package com.moodsinger.ccrt_clinic.shared.dto;

public class DoctorScheduleDto {
  private String doctorScheduleId;
  private UserDto user;
  private DayDto day;

  public String getDoctorScheduleId() {
    return doctorScheduleId;
  }

  public void setDoctorScheduleId(String doctorScheduleId) {
    this.doctorScheduleId = doctorScheduleId;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public DayDto getDay() {
    return day;
  }

  public void setDay(DayDto day) {
    this.day = day;
  }
}
