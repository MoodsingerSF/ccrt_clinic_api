package com.moodsinger.ccrt_clinic.model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BlogCreationRequestModel {
  private String title;
  private String description;
  private List<String> tagStrings;
  private String creatorUserId;
  private MultipartFile image;

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

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
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
    return "BlogCreationRequestModel [creatorUserId=" + creatorUserId + ", description=" + description + ", image="
        + image + ", tagStrings=" + tagStrings + ", title=" + title + "]";
  }

}
