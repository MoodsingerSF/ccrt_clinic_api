package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Entity
@Table(name = "donation_request")
public class DonationRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false, length = 20)
  private String requestId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private UserEntity requestor;

  @Column(nullable = false, length = 20)
  private String phoneNo;

  @Column(nullable = false)
  private double amount;

  @Column(length = 50000)
  private String description;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VerificationStatus requestStatus = VerificationStatus.PENDING;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CompletionStatus completionStatus = CompletionStatus.INCOMPLETE;

  @OneToMany(mappedBy = "donationRequest", fetch = FetchType.LAZY)
  private Set<DonationEntity> donations = new HashSet<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserEntity getRequestor() {
    return requestor;
  }

  public void setRequestor(UserEntity requestor) {
    this.requestor = requestor;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public VerificationStatus getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(VerificationStatus requestStatus) {
    this.requestStatus = requestStatus;
  }

  public CompletionStatus getCompletionStatus() {
    return completionStatus;
  }

  public void setCompletionStatus(CompletionStatus completionStatus) {
    this.completionStatus = completionStatus;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Set<DonationEntity> getDonations() {
    return donations;
  }

  public void setDonations(Set<DonationEntity> donations) {
    this.donations = donations;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
