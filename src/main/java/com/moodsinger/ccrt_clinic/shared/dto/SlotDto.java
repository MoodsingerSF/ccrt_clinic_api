package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;

import com.moodsinger.ccrt_clinic.io.enums.ActiveStatus;

public class SlotDto {

  private String slotId;
  private Date startTime;
  private Date endTime;
  private DoctorScheduleDto doctorScheduleDto;
  private boolean isEnabled;
  private String userId;
  private String dayCode;
  private String status;

  public String getSlotId() {
    return slotId;
  }

  public void setSlotId(String slotId) {
    this.slotId = slotId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public DoctorScheduleDto getDoctorScheduleDto() {
    return doctorScheduleDto;
  }

  public void setDoctorScheduleDto(DoctorScheduleDto doctorScheduleDto) {
    this.doctorScheduleDto = doctorScheduleDto;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDayCode() {
    return dayCode;
  }

  public void setDayCode(String dayCode) {
    this.dayCode = dayCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    this.isEnabled = status.equals(ActiveStatus.ENABLED.name());
  }

  @Override
  public String toString() {
    return "SlotDto [dayCode=" + dayCode + ", doctorScheduleDto=" + doctorScheduleDto + ", endTime=" + endTime
        + ", isEnabled=" + isEnabled + ", slotId=" + slotId + ", startTime=" + startTime + ", status=" + status
        + ", userId=" + userId + "]";
  }

}
