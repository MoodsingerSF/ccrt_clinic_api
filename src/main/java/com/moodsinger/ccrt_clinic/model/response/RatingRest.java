package com.moodsinger.ccrt_clinic.model.response;

public class RatingRest {
  private long id;
  private RatingCriteriaRest ratingCriteria;
  private double rating;

  public RatingCriteriaRest getRatingCriteria() {
    return ratingCriteria;
  }

  public void setRatingCriteria(RatingCriteriaRest ratingCriteria) {
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
}
