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

@Entity
@Table(name = "donation")
public class DonationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 20)
  private String donationId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private UserEntity donor;

  @Column(nullable = false)
  private double amount;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private DonationRequestEntity donationRequest;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDonationId() {
    return donationId;
  }

  public void setDonationId(String donationId) {
    this.donationId = donationId;
  }

  public UserEntity getDonor() {
    return donor;
  }

  public void setDonor(UserEntity donor) {
    this.donor = donor;
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

  public DonationRequestEntity getDonationRequest() {
    return donationRequest;
  }

  public void setDonationRequest(DonationRequestEntity donationRequest) {
    this.donationRequest = donationRequest;
  }
}
