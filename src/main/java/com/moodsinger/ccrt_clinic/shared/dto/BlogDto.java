package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class BlogDto {
  private String blogId;
  private String title;
  private String description;
  private String imageUrl;
  private List<String> tagStrings;
  private UserDto creator;
  private String creatorUserId;
  private Date creationTime;
  private MultipartFile image;
  private Set<TagDto> tags = new HashSet<>();
  private int totalBlogs;
  private long numTimesRead;

  public BlogDto() {
  }

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

  public UserDto getCreator() {
    return creator;
  }

  public void setCreator(UserDto creator) {
    this.creator = creator;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
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
    return "BlogDto [blogId=" + blogId + ", creationTime=" + creationTime + ", creator=" + creator + ", creatorUserId="
        + creatorUserId + ", description=" + description + ", image=" + image + ", imageUrl=" + imageUrl
        + ", tagStrings=" + tagStrings + ", title=" + title + "]";
  }

  public Set<TagDto> getTags() {
    return tags;
  }

  public void setTags(Set<TagDto> tags) {
    this.tags = tags;
  }

  public int getTotalBlogs() {
    return this.totalBlogs;
  }

  public void setTotalBlogs(int totalBlogs) {
    this.totalBlogs = totalBlogs;
  }

  public long getNumTimesRead() {
    return numTimesRead;
  }

  public void setNumTimesRead(long numTimesRead) {
    this.numTimesRead = numTimesRead;
  }

}
