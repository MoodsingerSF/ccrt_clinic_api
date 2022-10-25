package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.HomeCoverType;

@Entity
@Table(name = "home_covers")
public class HomeCoverEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20, unique = true)
  private String coverId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private HomeCoverType type;

  @Column(nullable = false, length = 50)
  private String itemId;

  @Column(nullable = false, length = 150)
  private String imageUrl;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date creationTime;

  @Column(nullable = false)
  private int serialNo = 0;

  @Column(nullable = false)
  private boolean isVisible = true;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCoverId() {
    return coverId;
  }

  public void setCoverId(String coverId) {
    this.coverId = coverId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public int getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(int serialNo) {
    this.serialNo = serialNo;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public HomeCoverType getType() {
    return type;
  }

  public void setType(HomeCoverType type) {
    this.type = type;
  }
}
