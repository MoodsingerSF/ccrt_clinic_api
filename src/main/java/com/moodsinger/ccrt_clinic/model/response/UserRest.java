package com.moodsinger.ccrt_clinic.model.response;

import java.util.List;
import java.util.Set;

import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;

public class UserRest {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String profileImageUrl;
  private Set<RoleDto> roles;
  private String gender;
  private String specialization;
  private String birthDate;
  private String about;
  private List<EducationRest> education;
  private List<TrainingRest> trainings;
  private List<ExperienceRest> experiences;
  private List<AwardRest> awards;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public Set<RoleDto> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDto> roles) {
    this.roles = roles;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public List<EducationRest> getEducation() {
    return education;
  }

  public void setEducation(List<EducationRest> education) {
    this.education = education;
  }

  public List<TrainingRest> getTrainings() {
    return trainings;
  }

  public void setTrainings(List<TrainingRest> trainings) {
    this.trainings = trainings;
  }

  public List<ExperienceRest> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<ExperienceRest> experiences) {
    this.experiences = experiences;
  }

  public List<AwardRest> getAwards() {
    return awards;
  }

  public void setAwards(List<AwardRest> awards) {
    this.awards = awards;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

}
