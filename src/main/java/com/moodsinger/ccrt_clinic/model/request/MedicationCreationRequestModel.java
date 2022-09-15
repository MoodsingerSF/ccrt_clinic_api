package com.moodsinger.ccrt_clinic.model.request;

import com.moodsinger.ccrt_clinic.io.enums.RelationWithMeal;

public class MedicationCreationRequestModel {
  private String medicineName;
  private boolean hasFixedScheduleRelatedToPhaseOfDay;
  private String schedule;
  private boolean takeInMorning;
  private boolean takeInNoon;
  private boolean takeInNight;
  private String timeGapWithMeal;
  private RelationWithMeal relationWithMeal;
  private String duration;
  private String advice;

  public String getMedicineName() {
    return medicineName;
  }

  public void setMedicineName(String medicineName) {
    this.medicineName = medicineName;
  }

  public boolean isHasFixedScheduleRelatedToPhaseOfDay() {
    return hasFixedScheduleRelatedToPhaseOfDay;
  }

  public void setHasFixedScheduleRelatedToPhaseOfDay(boolean hasFixedScheduleRelatedToPhaseOfDay) {
    this.hasFixedScheduleRelatedToPhaseOfDay = hasFixedScheduleRelatedToPhaseOfDay;
  }

  public String getSchedule() {
    return schedule;
  }

  public void setSchedule(String schedule) {
    this.schedule = schedule;
  }

  public boolean isTakeInMorning() {
    return takeInMorning;
  }

  public void setTakeInMorning(boolean takeInMorning) {
    this.takeInMorning = takeInMorning;
  }

  public boolean isTakeInNoon() {
    return takeInNoon;
  }

  public void setTakeInNoon(boolean takeInNoon) {
    this.takeInNoon = takeInNoon;
  }

  public boolean isTakeInNight() {
    return takeInNight;
  }

  public void setTakeInNight(boolean takeInNight) {
    this.takeInNight = takeInNight;
  }

  public String getTimeGapWithMeal() {
    return timeGapWithMeal;
  }

  public void setTimeGapWithMeal(String timeGapWithMeal) {
    this.timeGapWithMeal = timeGapWithMeal;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public RelationWithMeal getRelationWithMeal() {
    return relationWithMeal;
  }

  public void setRelationWithMeal(RelationWithMeal relationWithMeal) {
    this.relationWithMeal = relationWithMeal;
  }
}
