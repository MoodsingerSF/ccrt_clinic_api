package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Entity
@Table(name = "blogs")
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 30)
  private String blogId;

  @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH }, fetch = FetchType.EAGER)
  private UserEntity creator;

  @Column(nullable = false)
  private String title;

  @Column(length = 1000)
  private String searchColumn;

  @Column(nullable = false, length = 50000)
  private String description;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date creationTime;

  @Column(nullable = false)
  private boolean validity = true;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VerificationStatus verificationStatus = VerificationStatus.PENDING;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date lastModificationTime;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH }, fetch = FetchType.EAGER)
  @JoinTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagEntity> tags = new HashSet<>();

  private long numTimesRead = 0;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

  public UserEntity getCreator() {
    return creator;
  }

  public void setCreator(UserEntity creator) {
    this.creator = creator;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public boolean isValidity() {
    return validity;
  }

  public void setValidity(boolean validity) {
    this.validity = validity;
  }

  public VerificationStatus getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(VerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public Date getLastModificationTime() {
    return lastModificationTime;
  }

  public void setLastModificationTime(Date lastModificationTime) {
    this.lastModificationTime = lastModificationTime;
  }

  public Set<TagEntity> getTags() {
    return tags;
  }

  public void setTags(Set<TagEntity> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "BlogEntity [blogId=" + blogId + ", creationTime=" + creationTime + ", creator=" + creator + ", description="
        + description + ", id=" + id + ", imageUrl=" + imageUrl + ", lastModificationTime=" + lastModificationTime
        + ", tags=" + ", title=" + title + ", validity=" + validity + ", verificationStatus="
        + verificationStatus + "]";
  }

  public long getNumTimesRead() {
    return numTimesRead;
  }

  public void setNumTimesRead(long numTimesRead) {
    this.numTimesRead = numTimesRead;
  }

  public String getSearchColumn() {
    return searchColumn;
  }

  public void setSearchColumn(String searchColumn) {
    this.searchColumn = searchColumn;
  }
}
