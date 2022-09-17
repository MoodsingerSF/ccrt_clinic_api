package com.moodsinger.ccrt_clinic.shared.dto;

public class RatingDto {
  private RatingCriteriaDto ratingCriteria;
  private double rating;
  private long id;

  public RatingCriteriaDto getRatingCriteria() {
    return ratingCriteria;
  }

  public void setRatingCriteria(RatingCriteriaDto ratingCriteria) {
    this.ratingCriteria = ratingCriteria;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
};
