package com.moodsinger.ccrt_clinic.model.response;

import java.util.List;

public class RatingAndCriteriaList {
  private List<RatingRest> ratings;
  private List<RatingCriteriaRest> criteria;

  public List<RatingRest> getRatings() {
    return ratings;
  }

  public void setRatings(List<RatingRest> ratings) {
    this.ratings = ratings;
  }

  public List<RatingCriteriaRest> getCriteria() {
    return criteria;
  }

  public void setCriteria(List<RatingCriteriaRest> criteria) {
    this.criteria = criteria;
  }
}
