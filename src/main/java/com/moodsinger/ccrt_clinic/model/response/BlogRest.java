package com.moodsinger.ccrt_clinic.model.response;

import java.util.Date;
import java.util.Set;

public class BlogRest {
  private String blogId;
  private String title;
  private String description;
  private String imageUrl;
  private Set<TagRest> tags;
  private UserRest creator;
  private Date creationTime;

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public UserRest getCreator() {
    return creator;
  }

  public void setCreator(UserRest creator) {
    this.creator = creator;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Set<TagRest> getTags() {
    return tags;
  }

  public void setTags(Set<TagRest> tags) {
    this.tags = tags;
  }
}
