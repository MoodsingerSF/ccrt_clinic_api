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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "prescription")
public class PrescriptionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, length = 20, nullable = false)
  private String prescriptionId;

  @OneToOne(mappedBy = "prescription", fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private AppointmentEntity appointment;

  @OneToMany(mappedBy = "prescription", fetch = FetchType.EAGER)
  private Set<MedicationEntity> medications;

  private String advice;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Set<MedicationEntity> getMedications() {
    return medications;
  }

  public void setMedications(Set<MedicationEntity> medications) {
    this.medications = medications;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public AppointmentEntity getAppointment() {
    return appointment;
  }

  public void setAppointment(AppointmentEntity appointment) {
    this.appointment = appointment;
  }

  public String getPrescriptionId() {
    return prescriptionId;
  }

  public void setPrescriptionId(String prescriptionId) {
    this.prescriptionId = prescriptionId;
  }
}
