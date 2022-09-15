package com.moodsinger.ccrt_clinic.shared.dto;

public class AwardDto {
  private long id;
  private String name;
  private String year;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
