package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.shared.dto.AwardDto;
import com.moodsinger.ccrt_clinic.shared.dto.EducationDto;
import com.moodsinger.ccrt_clinic.shared.dto.ExperienceDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;
import com.moodsinger.ccrt_clinic.shared.dto.TrainingDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDetails);

  UserDto getUserByUserId(String userId);

  UserDto getUserByEmail(String userId);

  UserDto updateUser(String userId, UserDto updateDetails);

  UserDto updateUserRole(String userId, UserDto updateDetails);

  UserDto updateUserVerificationStatus(String userId, UserDto updateDetails);

  UserDto updateProfilePicture(String userId, MultipartFile image);

  List<UserDto> getDoctors(int page, int limit, VerificationStatus verificationStatus);

  ResourceDto addResource(String userId, String title, MultipartFile image);

  List<ResourceDto> getResources(String userId);

  ResourceDto updateResource(String userId, String resourceId,
      MultipartFile image);

  EducationDto addEducation(String userId, EducationDto educationDto);

  EducationDto updateEducation(String userId, long educationId, EducationDto educationDto);

  void deleteEducation(String userId, long educationId);

  TrainingDto addTraining(String userId, TrainingDto trainingDto);

  TrainingDto updateTraining(String userId, long trainingId, TrainingDto trainingDto);

  void deleteTraining(String userId, long trainingId);

  AwardDto addAward(String userId, AwardDto awardDto);

  AwardDto updateAward(String userId, long awardId, AwardDto awardDto);

  void deleteAward(String userId, long awardId);

  ExperienceDto addExperience(String userId, ExperienceDto experienceDto);

  ExperienceDto updateExperience(String userId, long experienceId, ExperienceDto experienceDto);

  void deleteExperience(String userId, long experienceId);
}
