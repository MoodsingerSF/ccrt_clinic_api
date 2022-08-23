package com.moodsinger.ccrt_clinic.model.request;

import java.util.List;

public class BlogCreationRequestModel {
  private String title;
  private String description;
  private List<String> tagStrings;
  private String creatorUserId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreatorUserId() {
    return creatorUserId;
  }

  public void setCreatorUserId(String creatorUserId) {
    this.creatorUserId = creatorUserId;
  }

  public List<String> getTagStrings() {
    return tagStrings;
  }

  public void setTagStrings(List<String> tagStrings) {
    this.tagStrings = tagStrings;
  }

  @Override
  public String toString() {
    return "BlogCreationRequestModel [creatorUserId=" + creatorUserId + ", description=" + description + ", tagStrings="
        + tagStrings + ", title=" + title + "]";
  }

}
