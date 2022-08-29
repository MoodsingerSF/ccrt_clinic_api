package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "slot", uniqueConstraints = {
    @UniqueConstraint(name = "unique_time_range", columnNames = { "doctor_schedule_entity_id", "startTime",
        "endTime" }) })
public class SlotEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false, length = 30)
  private String slotId;

  @ManyToOne
  @JoinColumn(nullable = false)
  private DoctorScheduleEntity doctorScheduleEntity;

  @Column(nullable = false)
  @Temporal(TemporalType.TIME)
  private Date startTime;

  @Column(nullable = false)
  @Temporal(TemporalType.TIME)
  private Date endTime;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date creationTime;

  @Column(nullable = false)
  private boolean isEnabled = false;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSlotId() {
    return slotId;
  }

  public void setSlotId(String slotId) {
    this.slotId = slotId;
  }

  public DoctorScheduleEntity getDoctorScheduleEntity() {
    return doctorScheduleEntity;
  }

  public void setDoctorScheduleEntity(DoctorScheduleEntity doctorScheduleEntity) {
    this.doctorScheduleEntity = doctorScheduleEntity;
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

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }
}
