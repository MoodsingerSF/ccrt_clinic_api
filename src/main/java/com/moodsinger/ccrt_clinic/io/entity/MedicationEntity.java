package com.moodsinger.ccrt_clinic.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.moodsinger.ccrt_clinic.io.enums.RelationWithMeal;

@Entity
@Table(name = "medication")
public class MedicationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private PrescriptionEntity prescription;

  @Column(nullable = false, length = 250)
  private String medicineName;

  @Column(nullable = false)
  private boolean hasFixedScheduleRelatedToPhaseOfDay;

  @Column(nullable = true, length = 250)
  private String schedule;

  private boolean takeInMorning;

  private boolean takeInNoon;

  private boolean takeInNight;

  private String timeGapWithMeal;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RelationWithMeal relationWithMeal;

  @Column(nullable = false)
  private String duration;

  @Column(nullable = true, length = 1000)
  private String advice;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PrescriptionEntity getPrescription() {
    return prescription;
  }

  public void setPrescription(PrescriptionEntity prescription) {
    this.prescription = prescription;
  }

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

  public String getTimeGapWithMeal() {
    return timeGapWithMeal;
  }

  public void setTimeGapWithMeal(String timeGapWithMeal) {
    this.timeGapWithMeal = timeGapWithMeal;
  }

  public RelationWithMeal getRelationWithMeal() {
    return relationWithMeal;
  }

  public void setRelationWithMeal(RelationWithMeal relationWithMeal) {
    this.relationWithMeal = relationWithMeal;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
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

}
