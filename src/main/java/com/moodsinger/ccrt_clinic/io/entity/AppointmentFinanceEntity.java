package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.moodsinger.ccrt_clinic.io.enums.FinanceType;

@Entity
@Table(name = "appointment_finance")
public class AppointmentFinanceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AppointmentEntity appointment;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FinanceType financeType;

  @Column(nullable = false)
  private double amount;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date creationTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public AppointmentEntity getAppointment() {
    return appointment;
  }

  public void setAppointment(AppointmentEntity appointment) {
    this.appointment = appointment;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public FinanceType getFinanceType() {
    return financeType;
  }

  public void setFinanceType(FinanceType financeType) {
    this.financeType = financeType;
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
}
