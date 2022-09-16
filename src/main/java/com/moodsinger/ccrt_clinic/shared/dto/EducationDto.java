package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;

public class EducationDto {

  private long id;
  private String degree;
  private String subject;
  private String institutionName;
  private Date startDate;
  private Date endDate;

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "EducationDto [degree=" + degree + ", endDate=" + endDate + ", id=" + id + ", institutionName="
        + institutionName + ", startDate=" + startDate + ", subject=" + subject + "]";
  }
}
