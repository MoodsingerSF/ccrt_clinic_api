package com.moodsinger.ccrt_clinic.model.request;

public class RatingInSingleCriteriaCreationRequestModel {
  private long id;
  private double rating;

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
