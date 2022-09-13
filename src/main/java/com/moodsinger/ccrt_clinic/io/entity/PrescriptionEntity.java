package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prescription")
public class PrescriptionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(mappedBy = "prescription")
  private Set<MedicationEntity> medications;

  private String advice;

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
}
