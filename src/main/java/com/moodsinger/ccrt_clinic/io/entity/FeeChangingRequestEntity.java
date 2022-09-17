package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Entity
@Table(name = "fee_changing_request")
public class FeeChangingRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 20)
  private String requestId;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;

  @Column(nullable = false)
  private double amount;

  @Column(nullable = false)
  private double previousAmount;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = false)
  private VerificationStatus status = VerificationStatus.PENDING;

  @Temporal(TemporalType.TIMESTAMP)
  private Date resolvedAt;

  @ManyToOne
  private UserEntity resolver;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getPreviousAmount() {
    return previousAmount;
  }

  public void setPreviousAmount(double previousAmount) {
    this.previousAmount = previousAmount;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public VerificationStatus getStatus() {
    return status;
  }

  public void setStatus(VerificationStatus status) {
    this.status = status;
  }

  public Date getResolvedAt() {
    return resolvedAt;
  }

  public void setResolvedAt(Date resolvedAt) {
    this.resolvedAt = resolvedAt;
  }

  public UserEntity getResolver() {
    return resolver;
  }

  public void setResolver(UserEntity resolver) {
    this.resolver = resolver;
  }

}
