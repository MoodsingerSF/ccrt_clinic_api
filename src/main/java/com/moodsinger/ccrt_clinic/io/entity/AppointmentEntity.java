package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "appointment")
public class AppointmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private SlotEntity slot;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_user_id", nullable = false)
  private UserEntity patient;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private long fee;

  @Column(nullable = false, length = 200)
  private String meetingLink;

  @Column(nullable = false, length = 10)
  private String patientVerificationCode;

  @OneToMany(mappedBy = "appointment")
  private Set<AppointmentResourceEntity> appointmentResources;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public SlotEntity getSlot() {
    return slot;
  }

  public void setSlot(SlotEntity slot) {
    this.slot = slot;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public long getFee() {
    return fee;
  }

  public void setFee(long fee) {
    this.fee = fee;
  }

  public String getMeetingLink() {
    return meetingLink;
  }

  public void setMeetingLink(String meetingLink) {
    this.meetingLink = meetingLink;
  }

  public String getPatientVerificationCode() {
    return patientVerificationCode;
  }

  public void setPatientVerificationCode(String patientVerificationCode) {
    this.patientVerificationCode = patientVerificationCode;
  }

  public UserEntity getPatient() {
    return patient;
  }

  public void setPatient(UserEntity patient) {
    this.patient = patient;
  }

  public Set<AppointmentResourceEntity> getAppointmentResources() {
    return appointmentResources;
  }

  public void setAppointmentResources(Set<AppointmentResourceEntity> appointmentResources) {
    this.appointmentResources = appointmentResources;
  }
}
