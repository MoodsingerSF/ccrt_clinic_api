package com.moodsinger.ccrt_clinic.io.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "doctor_schedule", uniqueConstraints = {
    @UniqueConstraint(name = "unique_doctor_schedule_week_day", columnNames = { "user_id", "day_id" }) })
public class DoctorScheduleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false, length = 30)
  private String doctorScheduleId;

  @ManyToOne(cascade = CascadeType.ALL)
  private UserEntity user;

  @ManyToOne
  private DayEntity day;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public DayEntity getDay() {
    return day;
  }

  public void setDay(DayEntity day) {
    this.day = day;
  }

  public String getDoctorScheduleId() {
    return doctorScheduleId;
  }

  public void setDoctorScheduleId(String doctorScheduleId) {
    this.doctorScheduleId = doctorScheduleId;
  }
}
