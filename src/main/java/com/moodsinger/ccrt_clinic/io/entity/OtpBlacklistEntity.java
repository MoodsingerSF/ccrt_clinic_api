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
@Table(name = "otp_blacklist")
public class OtpBlacklistEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 30)
  private String otpBlacklistId;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date timeOfBlocking;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date timeOfUnblocking;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOtpBlacklistId() {
    return otpBlacklistId;
  }

  public void setOtpBlacklistId(String otpBlacklistId) {
    this.otpBlacklistId = otpBlacklistId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getTimeOfBlocking() {
    return timeOfBlocking;
  }

  public void setTimeOfBlocking(Date timeOfBlocking) {
    this.timeOfBlocking = timeOfBlocking;
  }

  public Date getTimeOfUnblocking() {
    return timeOfUnblocking;
  }

  public void setTimeOfUnblocking(Date timeOfUnblocking) {
    this.timeOfUnblocking = timeOfUnblocking;
  }
}
