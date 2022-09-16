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

  TrainingDto addTraining(String userId, TrainingDto trainingDto);

  AwardDto addAward(String userId, AwardDto awardDto);

  ExperienceDto addExperience(String userId, ExperienceDto experienceDto);

}
