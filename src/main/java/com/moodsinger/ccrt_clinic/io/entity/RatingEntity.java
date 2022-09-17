package com.moodsinger.ccrt_clinic.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rating", uniqueConstraints = {
    @UniqueConstraint(name = "unique_rating", columnNames = { "patient_id", "doctor_id", "rating_criteria_id" }) })
public class RatingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private RatingCriteriaEntity ratingCriteria;

  @Column(nullable = false)
  private double rating;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UserEntity patient;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UserEntity doctor;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public RatingCriteriaEntity getRatingCriteria() {
    return ratingCriteria;
  }

  public void setRatingCriteria(RatingCriteriaEntity ratingCriteria) {
    this.ratingCriteria = ratingCriteria;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public UserEntity getPatient() {
    return patient;
  }

  public void setPatient(UserEntity patient) {
    this.patient = patient;
  }

  public UserEntity getDoctor() {
    return doctor;
  }

  public void setDoctor(UserEntity doctor) {
    this.doctor = doctor;
  }
}
