package com.moodsinger.ccrt_clinic.model.request;

// import java.text.DateFormat;
import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;

public class SlotCreationRequestModel {
  private String userId;
  private String dayCode;
  // private Date startTime;
  // private Date endTime;
  private String startTimeString;
  private String endTimeString;

  // public Date getStartTime() {
  // return startTime;
  // }

  // public void setStartTime(Date startTime) {
  // this.startTime = startTime;
  // }

  // public Date getEndTime() {
  // return endTime;
  // }

  // public void setEndTime(Date endTime) {
  // this.endTime = endTime;
  // }

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

  public String getStartTimeString() {
    return startTimeString;
  }

  public void setStartTimeString(String startTimeString) throws ParseException {
    this.startTimeString = startTimeString;
    // DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    // this.startTime = formatter.parse(startTimeString);
  }

  public String getEndTimeString() {
    return endTimeString;
  }

  public void setEndTimeString(String endTimeString) throws ParseException {
    this.endTimeString = endTimeString;
    // DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    // this.endTime = formatter.parse(endTimeString);
  }
}
