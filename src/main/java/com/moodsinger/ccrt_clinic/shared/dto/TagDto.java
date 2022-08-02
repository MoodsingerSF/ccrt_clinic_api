package com.moodsinger.ccrt_clinic.shared.dto;

public class TagDto {

  private String name;

  public TagDto() {
  }

  public TagDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "TagDto [" + "name=" + name + "]";
  }

}
