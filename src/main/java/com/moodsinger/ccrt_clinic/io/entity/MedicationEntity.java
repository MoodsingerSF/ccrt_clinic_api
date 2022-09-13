package com.moodsinger.ccrt_clinic.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  private String medicine_name;

  @Column(nullable = false)
  private boolean willTakeInMorning;

  @Column(nullable = false)
  private boolean willTakeInNoon;

  @Column(nullable = false)
  private boolean willTakeInNight;

  @Column(nullable = false)
  private boolean willTakeBeforeEating;

  @Column(nullable = false)
  private long duration;

  @Column(nullable = true, length = 1000)
  private String other;

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

  public String getMedicine_name() {
    return medicine_name;
  }

  public void setMedicine_name(String medicine_name) {
    this.medicine_name = medicine_name;
  }

  public boolean isWillTakeInMorning() {
    return willTakeInMorning;
  }

  public void setWillTakeInMorning(boolean willTakeInMorning) {
    this.willTakeInMorning = willTakeInMorning;
  }

  public boolean isWillTakeInNoon() {
    return willTakeInNoon;
  }

  public void setWillTakeInNoon(boolean willTakeInNoon) {
    this.willTakeInNoon = willTakeInNoon;
  }

  public boolean isWillTakeInNight() {
    return willTakeInNight;
  }

  public void setWillTakeInNight(boolean willTakeInNight) {
    this.willTakeInNight = willTakeInNight;
  }

  public boolean isWillTakeBeforeEating() {
    return willTakeBeforeEating;
  }

  public void setWillTakeBeforeEating(boolean willTakeBeforeEating) {
    this.willTakeBeforeEating = willTakeBeforeEating;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

}
