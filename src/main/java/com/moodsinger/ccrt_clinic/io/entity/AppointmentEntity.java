package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;

@Entity
@Table(name = "appointment", uniqueConstraints = {
    @UniqueConstraint(name = "unique_time_range", columnNames = { "slot_id", "date" }) })
public class AppointmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 15)
  private String appointmentId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "slot_id", nullable = false)
  private SlotEntity slot;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "patient_user_id", nullable = false)
  private UserEntity patient;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "doctor_user_id", nullable = false)
  private UserEntity doctor;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private long fee;

  @Column(nullable = false, length = 200)
  private String meetingLink;

  @Column(nullable = false, length = 10)
  private String patientVerificationCode;

  @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
  private Set<AppointmentResourceEntity> appointmentResources;

  @OneToOne(fetch = FetchType.LAZY)
  private PrescriptionEntity prescription = null;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status = AppointmentStatus.PENDING;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date cancellationTime;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date completionTime;

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

  public PrescriptionEntity getPrescription() {
    return prescription;
  }

  public void setPrescription(PrescriptionEntity prescription) {
    this.prescription = prescription;
  }

  public String getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(String appointmentId) {
    this.appointmentId = appointmentId;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getCancellationTime() {
    return cancellationTime;
  }

  public void setCancellationTime(Date cancellationTime) {
    this.cancellationTime = cancellationTime;
  }

  public Date getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(Date completionTime) {
    this.completionTime = completionTime;
  }

  public UserEntity getDoctor() {
    return doctor;
  }

  public void setDoctor(UserEntity doctor) {
    this.doctor = doctor;
  }

}
