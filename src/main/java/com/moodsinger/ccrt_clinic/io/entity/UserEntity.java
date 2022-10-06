package com.moodsinger.ccrt_clinic.io.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.moodsinger.ccrt_clinic.io.enums.Gender;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 30)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120, unique = true)
  private String email;

  @Column(nullable = false)
  private String encryptedPassword;

  @Column(nullable = true)
  private String profileImageUrl;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VerificationStatus verificationStatus = VerificationStatus.PENDING;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

  @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<BlogEntity> blogs = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<PatientReportEntity> patientReports = new HashSet<>();

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Transient
  private int age;

  @Column(nullable = true, length = 1000)
  private String about;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<ExperienceEntity> experiences;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<EducationEntity> education;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<TrainingEntity> trainings;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<AwardEntity> awards;

  @Column(nullable = true)
  private String resetPasswordToken;

  // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  // private Set<FeeChangingRequestEntity> feeChangingRequests = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<FinanceEntity> financeRecords = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "doctor_specialization", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
  private Set<SpecializationEntity> specializations = new HashSet<>();

  @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY)
  private Set<DonationEntity> donations = new HashSet<>();

  @OneToMany(mappedBy = "requestor", fetch = FetchType.LAZY)
  private Set<DonationRequestEntity> donationRequests = new HashSet<>();

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEncryptedPassword() {
    return this.encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public Set<RoleEntity> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }

  public VerificationStatus getVerificationStatus() {
    return verificationStatus;
  }

  public void setVerificationStatus(VerificationStatus verificationStatus) {
    this.verificationStatus = verificationStatus;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public Set<PatientReportEntity> getPatientReports() {
    return patientReports;
  }

  public void setPatientReports(Set<PatientReportEntity> patientReports) {
    this.patientReports = patientReports;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Set<ExperienceEntity> getExperiences() {
    return experiences;
  }

  public void setExperiences(Set<ExperienceEntity> experiences) {
    this.experiences = experiences;
  }

  public Set<AwardEntity> getAwards() {
    return awards;
  }

  public void setAwards(Set<AwardEntity> awards) {
    this.awards = awards;
  }

  public Set<EducationEntity> getEducation() {
    return education;
  }

  public void setEducation(Set<EducationEntity> education) {
    this.education = education;
  }

  public Set<TrainingEntity> getTrainings() {
    return trainings;
  }

  public void setTrainings(Set<TrainingEntity> trainings) {
    this.trainings = trainings;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  // public Set<FeeChangingRequestEntity> getFeeChangingRequests() {
  // return feeChangingRequests;
  // }

  // public void setFeeChangingRequests(Set<FeeChangingRequestEntity>
  // feeChangingRequests) {
  // this.feeChangingRequests = feeChangingRequests;
  // }

  public Set<FinanceEntity> getFinanceRecords() {
    return financeRecords;
  }

  public void setFinanceRecords(Set<FinanceEntity> financeRecords) {
    this.financeRecords = financeRecords;
  }

  @Override
  public String toString() {
    return "UserEntity [about=" + about + ", age=" + age + ", awards=" + awards + ", birthDate=" + birthDate
        + ", blogs=" + ", education=" + education + ", email=" + email + ", encryptedPassword="
        + encryptedPassword + ", experiences=" + experiences + ", feeChangingRequests="
        + ", financeRecords=" + ", firstName=" + firstName + ", gender=" + gender + ", id=" + id
        + ", lastName=" + lastName + ", patientReports=" + patientReports + ", profileImageUrl=" + profileImageUrl
        + ", roles=" + roles + ", trainings=" + trainings + ", userId=" + userId
        + ", verificationStatus=" + verificationStatus + "]";
  }

  public Set<BlogEntity> getBlogs() {
    return blogs;
  }

  public void setBlogs(Set<BlogEntity> blogs) {
    this.blogs = blogs;
  }

  public Set<SpecializationEntity> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(Set<SpecializationEntity> specializations) {
    this.specializations = specializations;
  }

  public String getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  public Set<DonationEntity> getDonations() {
    return donations;
  }

  public void setDonations(Set<DonationEntity> donations) {
    this.donations = donations;
  }

  public Set<DonationRequestEntity> getDonationRequests() {
    return donationRequests;
  }

  public void setDonationRequests(Set<DonationRequestEntity> donationRequests) {
    this.donationRequests = donationRequests;
  }

}
