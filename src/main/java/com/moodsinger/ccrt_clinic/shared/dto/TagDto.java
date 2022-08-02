package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.List;

public class TagDto {

  private String name;
  private List<BlogDto> blogs;

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

  public List<BlogDto> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<BlogDto> blogs) {
    this.blogs = blogs;
  }

  @Override
  public String toString() {
    return "TagDto [blogs=" + blogs + ", name=" + name + "]";
  }

}
