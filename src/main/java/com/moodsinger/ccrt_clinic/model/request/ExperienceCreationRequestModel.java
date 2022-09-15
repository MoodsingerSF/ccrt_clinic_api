package com.moodsinger.ccrt_clinic.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ExperienceCreationRequestModel {
  private String title;
  private String organization;
  private String division;
  private String department;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getDivision() {
    return division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
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
}
