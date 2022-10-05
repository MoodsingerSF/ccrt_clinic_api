package com.moodsinger.ccrt_clinic.model.request;

public class RatingInSingleCriteriaCreationRequestModel {
  private long criteriaId;
  private double rating;

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public long getCriteriaId() {
    return criteriaId;
  }

  public void setCriteriaId(long criteriaId) {
    this.criteriaId = criteriaId;
  }

  @Override
  public String toString() {
    return "RatingInSingleCriteriaCreationRequestModel [criteriaId=" + criteriaId + ", rating=" + rating + "]";
  }

}
