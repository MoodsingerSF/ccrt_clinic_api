package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "otp")
public class OtpEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 30)
  private String otpId;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = false, length = 10)
  private String code;

  private Date invalidationTime;

  private Date otpUsedAt;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getInvalidationTime() {
    return invalidationTime;
  }

  public void setInvalidationTime(Date invalidationTime) {
    this.invalidationTime = invalidationTime;
  }

  public Date getOtpUsedAt() {
    return otpUsedAt;
  }

  public void setOtpUsedAt(Date otpUsedAt) {
    this.otpUsedAt = otpUsedAt;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }
}
