package com.moodsinger.ccrt_clinic.io.entity;

public class AverageRating {

  public AverageRating(RatingCriteriaEntity ratingCriteria, double rating, UserEntity doctor) {
    this.ratingCriteria = ratingCriteria;
    this.rating = rating;
    this.doctor = doctor;
  }

  private RatingCriteriaEntity ratingCriteria;
  private double rating;
  private UserEntity doctor;

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

  public UserEntity getDoctor() {
    return doctor;
  }

  public void setDoctor(UserEntity doctor) {
    this.doctor = doctor;
  }

}
