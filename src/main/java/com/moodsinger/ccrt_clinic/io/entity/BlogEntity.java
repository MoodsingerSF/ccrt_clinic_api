package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
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

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Entity
@Table(name = "blogs")
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long blog;

  @Column(nullable = false, length = 30)
  private String blogId;

  @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH }, fetch = FetchType.EAGER)
  private UserEntity user;

  @Column(nullable = false)
  private String blogTitle;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = false)
  private boolean validity = true;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VerificationStatus verificationStatus;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date lastModificationTime;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagEntity> tags;

  public long getBlog() {
    return blog;
  }

  public void setBlog(long blog) {
    this.blog = blog;
  }

  public String getBlogId() {
    return blogId;
  }

  public void setBlogId(String blogId) {
    this.blogId = blogId;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getBlogTitle() {
    return blogTitle;
  }

  public void setBlogTitle(String blogTitle) {
    this.blogTitle = blogTitle;
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

}
