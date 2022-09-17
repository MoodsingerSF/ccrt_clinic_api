package com.moodsinger.ccrt_clinic.model.request;

import java.util.List;

public class RatingCreationRequestModel {
  private String ratingGiverUserId;
  private List<RatingInSingleCriteriaCreationRequestModel> ratings;

  public String getRatingGiverUserId() {
    return ratingGiverUserId;
  }

  public void setRatingGiverUserId(String ratingGiverUserId) {
    this.ratingGiverUserId = ratingGiverUserId;
  }

  public List<RatingInSingleCriteriaCreationRequestModel> getRatings() {
    return ratings;
  }

  public void setRatings(List<RatingInSingleCriteriaCreationRequestModel> ratings) {
    this.ratings = ratings;
  }
}
