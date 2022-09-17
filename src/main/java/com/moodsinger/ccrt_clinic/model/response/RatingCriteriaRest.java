package com.moodsinger.ccrt_clinic.model.response;

public class RatingCriteriaRest {
  private long id;
  private String title;
  private double maxValue;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(double maxValue) {
    this.maxValue = maxValue;
  }
}
