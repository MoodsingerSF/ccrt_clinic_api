package com.moodsinger.ccrt_clinic.shared.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.moodsinger.ccrt_clinic.io.enums.Gender;

public class UserDto {
  private long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private Set<RoleDto> roles;
  private String userType;
  private String role;
  private String verificationStatus;
  private String profileImageUrl;
  private String code;
  private Gender gender;
  private String specialization;
  private Date birthDate;
  private String about;
  private List<EducationDto> education;
  private List<TrainingDto> trainings;
  private List<ExperienceDto> experiences;
  private List<AwardDto> awards;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public Set<RoleDto> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDto> roles) {
    this.roles = roles;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(String verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public List<EducationDto> getEducation() {
    return education;
  }

  public void setEducation(List<EducationDto> education) {
    this.education = education;
  }

  public List<TrainingDto> getTrainings() {
    return trainings;
  }

  public void setTrainings(List<TrainingDto> trainings) {
    this.trainings = trainings;
  }

  public List<ExperienceDto> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<ExperienceDto> experiences) {
    this.experiences = experiences;
  }

  public List<AwardDto> getAwards() {
    return awards;
  }

  public void setAwards(List<AwardDto> awards) {
    this.awards = awards;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }
}
