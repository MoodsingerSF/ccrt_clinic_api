package com.moodsinger.ccrt_clinic.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;
import com.moodsinger.ccrt_clinic.io.enums.Gender;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.AwardCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.EducationCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.ExperienceCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.TrainingCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.UserSignupRequestModel;
import com.moodsinger.ccrt_clinic.model.request.UserUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.AppointmentRest;
import com.moodsinger.ccrt_clinic.model.response.AwardRest;
import com.moodsinger.ccrt_clinic.model.response.BlogRest;
import com.moodsinger.ccrt_clinic.model.response.EducationRest;
import com.moodsinger.ccrt_clinic.model.response.ExperienceRest;
import com.moodsinger.ccrt_clinic.model.response.ResourceRest;
import com.moodsinger.ccrt_clinic.model.response.TrainingRest;
import com.moodsinger.ccrt_clinic.model.response.UserRest;
import com.moodsinger.ccrt_clinic.service.UserAppointmentService;
import com.moodsinger.ccrt_clinic.service.UserBlogService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.AwardDto;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.EducationDto;
import com.moodsinger.ccrt_clinic.shared.dto.ExperienceDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;
import com.moodsinger.ccrt_clinic.shared.dto.TrainingDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserBlogService userBlogService;

  @Autowired
  private UserAppointmentService userAppointmentService;

  @PostMapping
  public UserRest createUser(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    // check validity of request
    checkUserSignupRequestBody(userSignupRequestModel, false);

    // create user
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);

    // save user
    UserDto createdUserDto = userService.createUser(userDto);
    // returning created user
    UserRest userRest = modelMapper.map(createdUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping
  public UserRest getUser(@RequestParam("email") String email) {
    UserDto foundUserDto = userService.getUserByEmail(email);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping("/doctors")
  public List<UserRest> getDoctors(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(value = "status", required = false, defaultValue = "ACCEPTED") VerificationStatus verificationStatus) {
    List<UserDto> foundDoctors = userService.getDoctors(page, limit, verificationStatus);
    List<UserRest> foundDoctorsRest = new ArrayList<>();
    for (UserDto userDto : foundDoctors) {
      foundDoctorsRest.add(modelMapper.map(userDto, UserRest.class));
    }
    return foundDoctorsRest;
  }

  @GetMapping("/{userId}")
  public UserRest getUserDetails(@PathVariable String userId) {
    UserDto foundUserDto = userService.getUserByUserId(userId);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PutMapping("/{userId}")
  public UserRest updateUserDetails(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {

    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUser(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;

  }

  @PutMapping(path = "/{userId}/profile-picture", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public UserRest updateProfilePicture(@PathVariable String userId,
      @RequestPart("image") MultipartFile image) {
    UserDto createdUserDto = userService.updateProfilePicture(userId, image);
    UserRest createdUserRest = modelMapper.map(createdUserDto, UserRest.class);
    return createdUserRest;

  }

  @PutMapping("/{userId}/role")
  public UserRest updateUserRole(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {
    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUserRole(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PutMapping("/{userId}/verification-status")
  public UserRest updateUserVerificationStatus(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {

    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUserVerificationStatus(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PostMapping("/admin")
  public UserRest createAdmin(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    checkUserSignupRequestBody(userSignupRequestModel, true);
    userSignupRequestModel.setUserType(Role.ADMIN.name());
    // create user
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);
    // save user
    UserDto createdUserDto = userService.createUser(userDto);
    // returning created user
    UserRest userRest = modelMapper.map(createdUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping("/{userId}/blogs")
  public List<BlogRest> getUserBlogs(@PathVariable String userId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<BlogDto> blogDtos = userBlogService.getUserBlogs(userId, page, limit);
    List<BlogRest> blogRests = new ArrayList<>();
    for (BlogDto blogDto : blogDtos) {
      blogRests.add(modelMapper.map(blogDto, BlogRest.class));
    }
    return blogRests;
  }

  @GetMapping("/{userId}/appointments")
  public List<AppointmentRest> getUserAppointments(@PathVariable String userId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "date", required = true) Date date,
      @RequestParam(name = "status", required = false) AppointmentStatus status) {
    List<AppointmentDto> appointmentDtos = userAppointmentService.getUserAppointments(userId, page, limit, status,
        date);
    List<AppointmentRest> appointmentRests = new ArrayList<>();
    for (AppointmentDto appointmentDto : appointmentDtos) {
      appointmentRests.add(modelMapper.map(appointmentDto, AppointmentRest.class));
    }
    return appointmentRests;
  }

  // @PostMapping("/{userId}/appointments/{appointmentId}/cancel")
  // public AppointmentRest cancelAppointment(@PathVariable(name = "userId")
  // String userId,
  // @PathVariable(name = "appointmentId") String appointmentId) {
  // AppointmentDto appointmentDto =
  // userAppointmentService.cancelAppointment(userId, appointmentId);
  // return modelMapper.map(appointmentDto, AppointmentRest.class);
  // }
  @PostMapping("/{userId}/reports")
  public ResourceRest addReport(@PathVariable String userId,
      @RequestPart(name = "image", required = true) MultipartFile image,
      @RequestPart(name = "title", required = true) String title) {
    ResourceDto resourceDto = userService.addResource(userId, title, image);
    return modelMapper.map(resourceDto, ResourceRest.class);
  }

  @GetMapping("/{userId}/reports")
  public List<ResourceRest> retrieveReports(@PathVariable String userId) {
    List<ResourceDto> appointmentResources = userService.getResources(userId);
    List<ResourceRest> appointmentResourceRests = new ArrayList<>();
    for (ResourceDto resourceDto : appointmentResources) {
      appointmentResourceRests.add(modelMapper.map(resourceDto, ResourceRest.class));
    }

    return appointmentResourceRests;
  }

  @PutMapping("/{userId}/reports/{resourceId}")
  public ResourceRest updateReport(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "resourceId") String resourceId,
      @RequestPart(name = "image", required = true) MultipartFile image) {
    ResourceDto resourceDto = userService.updateResource(userId, resourceId,
        image);
    return modelMapper.map(resourceDto, ResourceRest.class);
  }

  @PostMapping("/{userId}/education")
  public EducationRest addEducation(@PathVariable(name = "userId") String userId,
      @RequestBody EducationCreationRequestModel educationCreationRequestModel) {
    EducationDto educationDto = userService.addEducation(userId,
        modelMapper.map(educationCreationRequestModel, EducationDto.class));
    return modelMapper.map(educationDto, EducationRest.class);
  }

  @PutMapping("/{userId}/education/{educationId}")
  public EducationRest updateEducation(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "educationId") long educationId,
      @RequestBody EducationCreationRequestModel educationCreationRequestModel) {
    EducationDto educationDto = userService.updateEducation(userId, educationId,
        modelMapper.map(educationCreationRequestModel, EducationDto.class));
    return modelMapper.map(educationDto, EducationRest.class);
  }

  @DeleteMapping("/{userId}/education/{educationId}")
  public ResponseEntity<String> deleteEducation(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "educationId") long educationId) {
    userService.deleteEducation(userId, educationId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

  }

  @PostMapping("/{userId}/training")
  public TrainingRest addTraining(@PathVariable(name = "userId") String userId,
      @RequestBody TrainingCreationRequestModel trainingCreationRequestModel) {
    TrainingDto trainingDto = userService.addTraining(userId,
        modelMapper.map(trainingCreationRequestModel, TrainingDto.class));
    return modelMapper.map(trainingDto, TrainingRest.class);
  }

  @PutMapping("/{userId}/training/{trainingId}")
  public TrainingRest updateTraining(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "trainingId") long trainingId,
      @RequestBody TrainingCreationRequestModel trainingCreationRequestModel) {
    TrainingDto trainingDto = userService.updateTraining(userId, trainingId,
        modelMapper.map(trainingCreationRequestModel, TrainingDto.class));
    return modelMapper.map(trainingDto, TrainingRest.class);
  }

  @DeleteMapping("/{userId}/training/{trainingId}")
  public ResponseEntity<String> deleteTraining(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "trainingId") long trainingId) {
    userService.deleteTraining(userId, trainingId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{userId}/experience")
  public ExperienceRest addExperience(@PathVariable(name = "userId") String userId,
      @RequestBody ExperienceCreationRequestModel experienceCreationRequestModel) {
    ExperienceDto experienceDto = userService.addExperience(userId,
        modelMapper.map(experienceCreationRequestModel, ExperienceDto.class));
    return modelMapper.map(experienceDto, ExperienceRest.class);
  }

  @PutMapping("/{userId}/experience/{experienceId}")
  public ExperienceRest updateExperience(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "experienceId") long experienceId,
      @RequestBody ExperienceCreationRequestModel experienceCreationRequestModel) {
    ExperienceDto experienceDto = userService.updateExperience(userId, experienceId,
        modelMapper.map(experienceCreationRequestModel, ExperienceDto.class));
    return modelMapper.map(experienceDto, ExperienceRest.class);
  }

  @DeleteMapping("/{userId}/experience/{experienceId}")
  public ResponseEntity<String> deleteExperience(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "experienceId") long experienceId) {
    userService.deleteExperience(userId, experienceId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{userId}/award")
  public AwardRest addAward(@PathVariable(name = "userId") String userId,
      @RequestBody AwardCreationRequestModel awardCreationRequestModel) {
    AwardDto awardDto = userService.addAward(userId,
        modelMapper.map(awardCreationRequestModel, AwardDto.class));
    return modelMapper.map(awardDto, AwardRest.class);
  }

  private void checkUserSignupRequestBody(UserSignupRequestModel userSignupRequestModel, boolean isUserTypeOptional) {
    String firstName = userSignupRequestModel.getFirstName();
    String lastName = userSignupRequestModel.getLastName();
    String email = userSignupRequestModel.getEmail();
    String password = userSignupRequestModel.getPassword();
    String userType = userSignupRequestModel.getUserType();
    Gender gender = userSignupRequestModel.getGender();
    Date birthDate = userSignupRequestModel.getBirthDate();
    String specialization = userSignupRequestModel.getSpecialization();

    if (!utils.isNonNullAndNonEmpty(firstName))
      throw new UserServiceException(ExceptionErrorCodes.FIRST_NAME_NOT_VALID.name(),
          ExceptionErrorMessages.FIRST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.isNonNullAndNonEmpty(lastName))
      throw new UserServiceException(ExceptionErrorCodes.LAST_NAME_NOT_VALID.name(),
          ExceptionErrorMessages.LAST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validateEmail(email))
      throw new UserServiceException(ExceptionErrorCodes.EMAIL_NOT_VALID.name(),
          ExceptionErrorMessages.EMAIL_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validatePassword(password))
      throw new UserServiceException(ExceptionErrorCodes.PASSWORD_NOT_VALID.name(),
          ExceptionErrorMessages.PASSWORD_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!isUserTypeOptional && !utils.validateUserType(userType))
      throw new UserServiceException(ExceptionErrorCodes.USER_TYPE_NOT_VALID.name(),
          ExceptionErrorMessages.USER_TYPE_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validateGender(gender))
      throw new UserServiceException(ExceptionErrorCodes.GENDER_NOT_VALID.name(),
          ExceptionErrorMessages.GENDER_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validateBirthDate(birthDate))
      throw new UserServiceException(ExceptionErrorCodes.BIRTH_DATE_NOT_VALID.name(),
          ExceptionErrorMessages.BIRTH_DATE_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (userType.equals(Role.DOCTOR.name()) && !utils.validateSpecialization(specialization)) {
      throw new UserServiceException(ExceptionErrorCodes.SPECIALIZATION_NOT_VALID.name(),
          ExceptionErrorMessages.SPECIALIZATION_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
