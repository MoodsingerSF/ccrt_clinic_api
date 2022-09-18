package com.moodsinger.ccrt_clinic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.AppProperties;
import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.AwardEntity;
import com.moodsinger.ccrt_clinic.io.entity.EducationEntity;
import com.moodsinger.ccrt_clinic.io.entity.ExperienceEntity;
import com.moodsinger.ccrt_clinic.io.entity.FeeChangingRequestEntity;
import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;
import com.moodsinger.ccrt_clinic.io.entity.PatientReportEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.TrainingEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.io.repository.AwardRepository;
import com.moodsinger.ccrt_clinic.io.repository.DoctorScheduleRepository;
import com.moodsinger.ccrt_clinic.io.repository.EducationRepository;
import com.moodsinger.ccrt_clinic.io.repository.ExperienceRepository;
import com.moodsinger.ccrt_clinic.io.repository.FeeChangingRequestRepository;
import com.moodsinger.ccrt_clinic.io.repository.PatientReportRepository;
import com.moodsinger.ccrt_clinic.io.repository.TrainingRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.DoctorScheduleService;
import com.moodsinger.ccrt_clinic.service.FeeChangingRequestService;
import com.moodsinger.ccrt_clinic.service.FeeService;
import com.moodsinger.ccrt_clinic.service.RoleService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.FileUploadUtil;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.AwardDto;
import com.moodsinger.ccrt_clinic.shared.dto.EducationDto;
import com.moodsinger.ccrt_clinic.shared.dto.ExperienceDto;
import com.moodsinger.ccrt_clinic.shared.dto.FeeChangingRequestDto;
import com.moodsinger.ccrt_clinic.shared.dto.FeeDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;
import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;
import com.moodsinger.ccrt_clinic.shared.dto.TrainingDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private Utils utils;

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private DoctorScheduleService doctorScheduleService;

  @Autowired
  private DoctorScheduleRepository doctorScheduleRepository;

  @Autowired
  private PatientReportRepository patientReportRepository;

  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private TrainingRepository trainingRepository;

  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  private AwardRepository awardRepository;

  @Autowired
  private FeeChangingRequestService feeChangingRequestService;

  @Autowired
  private FeeChangingRequestRepository feeChangingRequestRepository;

  @Autowired
  private FeeService feeService;

  @Transactional
  @Override
  public UserDto createUser(UserDto userDetails) {
    // create user entity
    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    // adding public user id
    String userId = utils.generateUserId(30);
    userEntity.setUserId(userId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
    if (userDetails.getUserType().equals(Role.USER.name()) || userDetails.getUserType().equals(Role.ADMIN.name())) {
      userEntity.setVerificationStatus(VerificationStatus.ACCEPTED);
    } else if (userDetails.getUserType().equals(Role.DOCTOR.name())) {
      userEntity.setVerificationStatus(VerificationStatus.PENDING);
    }

    Set<RoleEntity> roles = new HashSet<>();
    RoleEntity role = modelMapper.map(
        roleService.getOrCreateRole(userDetails.getUserType().equals(Role.ADMIN.name()) ? Role.ADMIN
            : userDetails.getUserType().equals(Role.DOCTOR.name()) ? Role.DOCTOR : Role.USER),
        RoleEntity.class);
    roles.add(role);
    // https://youtube.com/clip/UgkxSEHC1SCU_h5ppeoKllC4GFT9GNfvezxr
    userEntity.setRoles(roles);

    // save user entity
    UserEntity createdUserEntity = userRepository.save(userEntity);

    if (userDetails.getUserType().equals(Role.DOCTOR.name())) {
      FeeChangingRequestDto feeChangingRequestDto = new FeeChangingRequestDto();
      feeChangingRequestDto.setAmount(userDetails.getFee());
      feeChangingRequestDto.setPreviousAmount(-1);
      feeChangingRequestDto.setUserId(userId);
      feeChangingRequestDto.setStatus(VerificationStatus.PENDING);
      feeChangingRequestService.createFeeChangingRequest(feeChangingRequestDto);
    }

    if (createdUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_CREATED.name(),
          ExceptionErrorMessages.USER_NOT_CREATED.getMessage());
    else {
      if (userDetails.getUserType().equals(Role.DOCTOR.name())) {
        doctorScheduleService.initialize(createdUserEntity);
      }
    }

    UserDto userDto = modelMapper.map(createdUserEntity, UserDto.class);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    UserDto userDto = new ModelMapper().map(foundUserEntity, UserDto.class);
    FeeEntity feeEntity = feeService.getFeeOfDoctor(foundUserEntity.getUserId());
    if (feeEntity != null)
      userDto.setFee(feeEntity.getAmount());
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUser(String userId, UserDto updateDetails) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    if (utils.isNonNullAndNonEmpty(updateDetails.getFirstName()))
      foundUserEntity.setFirstName(updateDetails.getFirstName());
    if (utils.isNonNullAndNonEmpty(updateDetails.getLastName()))
      foundUserEntity.setLastName(updateDetails.getLastName());
    if (utils.isNonNullAndNonEmpty(updateDetails.getAbout()))
      foundUserEntity.setAbout(updateDetails.getAbout());
    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity foundUserEntity = userRepository.findByEmail(email);
    if (foundUserEntity == null)
      throw new UsernameNotFoundException(email);
    Set<RoleEntity> roles = foundUserEntity.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (RoleEntity role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName().name()));
    }
    return new User(foundUserEntity.getEmail(), foundUserEntity.getEncryptedPassword(),
        foundUserEntity.getVerificationStatus().equals(VerificationStatus.ACCEPTED), true, true, true,
        authorities);
  }

  @Transactional
  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity foundUserEntity = userRepository.findByEmail(email);
    if (foundUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    System.out.println(foundUserEntity);
    UserDto userDto = new ModelMapper().map(foundUserEntity, UserDto.class);
    // System.out.println(userDto);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUserRole(String userId, UserDto updateDetails) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    Role role = Role.valueOf(updateDetails.getRole());
    if (role == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_TYPE_NOT_VALID.name(),
          ExceptionErrorMessages.USER_TYPE_NOT_VALID.getMessage());
    }
    Set<RoleEntity> roles = new HashSet<>();
    RoleDto newRoleDto = roleService.getOrCreateRole(role);
    RoleEntity newRoleEntity = new ModelMapper().map(newRoleDto, RoleEntity.class);
    roles.add(newRoleEntity);
    foundUserEntity.setRoles(roles);
    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUserVerificationStatus(String userId, UserDto updateDetails) {
    if (!utils.validateVerificationStatus(updateDetails.getVerificationStatus()))
      throw new UserServiceException(ExceptionErrorCodes.MALFORMED_JSON_BODY.name(),
          ExceptionErrorMessages.MALFORMED_JSON_BODY.getMessage(), HttpStatus.BAD_REQUEST);

    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    if (foundUserEntity.getVerificationStatus() == VerificationStatus.ACCEPTED
        || foundUserEntity.getVerificationStatus() == VerificationStatus.REJECTED) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    VerificationStatus verificationStatus = VerificationStatus.valueOf(updateDetails.getVerificationStatus());
    if (verificationStatus == VerificationStatus.ACCEPTED) {
      List<FeeChangingRequestEntity> feeChangingRequestEntities = feeChangingRequestService
          .retrievePendingRequestEntitiesOfUser(userId);
      if (feeChangingRequestEntities != null) {
        FeeChangingRequestEntity latestFeeChangingRequestEntity = feeChangingRequestEntities.get(0);
        FeeDto feeDto = new FeeDto(latestFeeChangingRequestEntity.getAmount(), userId);
        feeService.addFee(feeDto);

        for (FeeChangingRequestEntity feeChangingRequestEntity : feeChangingRequestEntities) {
          feeChangingRequestEntity.setResolver(userRepository.findByUserId(updateDetails.getAdminUserId()));
          feeChangingRequestEntity.setStatus(VerificationStatus.ACCEPTED);
        }
        feeChangingRequestRepository.saveAll(feeChangingRequestEntities);
      }
      foundUserEntity.setVerificationStatus(verificationStatus);
      UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
      UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
      return userDto;
    } else if (verificationStatus == VerificationStatus.REJECTED) {
      List<FeeChangingRequestEntity> feeChangingRequestEntities = feeChangingRequestService
          .retrieveAllRequestEntitiesOfUser(userId);
      feeChangingRequestRepository.deleteAll(feeChangingRequestEntities);
      doctorScheduleRepository.deleteAll(doctorScheduleRepository.findByUserUserId(userId));
      userRepository.delete(foundUserEntity);
    }

    return new UserDto();
  }

  @Override
  public UserDto updateProfilePicture(String userId, MultipartFile image) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
    try {
      fileUploadUtil.saveFile(FileUploadUtil.PROFILE_PICTURE_UPLOAD_DIR + File.separator + userId,
          getFileName(fileName), image);
      foundUserEntity
          .setProfileImageUrl(getImageUrl(userId, fileName));
      UserEntity savedUserEntity = userRepository.save(foundUserEntity);
      UserDto userDto = modelMapper.map(savedUserEntity, UserDto.class);
      return userDto;
    } catch (IOException e) {
      throw new UserServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    }

  }

  private String getImageUrl(String userId, String fileName) {
    return appProperties.getProperty("baseUrl") + "users/" + userId + "/index." + utils.getFileExtension(fileName);
  }

  private String getFileName(String fileName) {
    return "index." + utils.getFileExtension(fileName);
  }

  @Override
  public List<UserDto> getDoctors(int page, int limit, VerificationStatus verificationStatus) {
    Pageable pageable = PageRequest.of(page, limit);
    Page<UserEntity> foundDoctorsPage = userRepository.findByRoleAndVerificationStatus(Role.DOCTOR, verificationStatus,
        pageable);
    List<UserEntity> foundDoctors = foundDoctorsPage.getContent();
    List<UserDto> foundDoctorsDto = new ArrayList<>();
    for (UserEntity userEntity : foundDoctors) {
      UserDto user = modelMapper.map(userEntity, UserDto.class);
      user.setFee(feeService.getFeeOfDoctor(userEntity.getUserId()).getAmount());
      foundDoctorsDto.add(user);
    }
    return foundDoctorsDto;
  }

  @Override
  public ResourceDto addResource(String userId, String title, MultipartFile image) {
    try {
      UserEntity user = userRepository.findByUserId(userId);
      if (user == null) {
        throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
            ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      String resourceId = utils.generateImageId();
      String url = fileUploadUtil.saveFile(
          FileUploadUtil.USER_REPORTS_UPLOAD_DIR + File.separator + userId,
          resourceId + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      PatientReportEntity patientResourceEntity = new PatientReportEntity();
      patientResourceEntity.setResourceId(resourceId);
      patientResourceEntity.setUser(user);
      patientResourceEntity.setTitle(title);
      patientResourceEntity.setImageUrl(url);
      PatientReportEntity createdReportEntity = patientReportRepository.save(patientResourceEntity);
      return modelMapper.map(createdReportEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new UserServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    }
  }

  @Override
  public List<ResourceDto> getResources(String userId) {
    List<PatientReportEntity> patientReportEntities = patientReportRepository
        .findAllByUserUserId(userId);
    List<ResourceDto> resourceDtos = new ArrayList<>();
    for (PatientReportEntity patientReportEntity : patientReportEntities) {
      resourceDtos.add(modelMapper.map(patientReportEntity, ResourceDto.class));
    }
    return resourceDtos;
  }

  @Override
  public ResourceDto updateResource(String userId, String resourceId, MultipartFile image) {
    try {
      PatientReportEntity patientReportEntity = patientReportRepository.findByResourceId(resourceId);
      if (patientReportEntity == null) {
        throw new UserServiceException(ExceptionErrorCodes.RESOURCE_NOT_FOUND.name(),
            ExceptionErrorMessages.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }

      if (!patientReportEntity.getUser().getUserId().equals(userId)) {
        throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
            ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      String url = fileUploadUtil.saveFile(
          FileUploadUtil.USER_REPORTS_UPLOAD_DIR + File.separator + userId,
          utils.generateImageId() + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      patientReportEntity.setImageUrl(url);
      PatientReportEntity updatedResourceEntity = patientReportRepository.save(patientReportEntity);
      return modelMapper.map(updatedResourceEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new UserServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    }
  }

  @Transactional
  @Override
  public EducationDto addEducation(String userId, EducationDto educationDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    EducationEntity educationEntity = modelMapper.map(educationDto, EducationEntity.class);
    educationEntity.setUser(userEntity);
    EducationEntity createdEducationEntity = educationRepository.save(educationEntity);
    return modelMapper.map(createdEducationEntity, EducationDto.class);
  }

  @Transactional
  @Override
  public EducationDto updateEducation(String userId, long educationId, EducationDto educationDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    EducationEntity educationEntity = educationRepository.findById(educationId);

    if (educationEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.EDUCATION_NOT_FOUND.name(),
          ExceptionErrorMessages.EDUCATION_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (!educationEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    if (utils.isNonNullAndNonEmpty(educationDto.getDegree())) {
      educationEntity.setDegree(educationDto.getDegree());
    }
    if (utils.isNonNullAndNonEmpty(educationDto.getInstitutionName())) {
      educationEntity.setInstitutionName(educationDto.getInstitutionName());
    }
    if (utils.isNonNullAndNonEmpty(educationDto.getSubject())) {
      educationEntity.setSubject(educationDto.getSubject());
    }
    if (utils.isNonNull(educationDto.getStartDate())) {
      educationEntity.setStartDate(educationDto.getStartDate());
    }
    if (utils.isNonNull(educationDto.getEndDate())) {
      educationEntity.setEndDate(educationDto.getEndDate());
    }

    EducationEntity updatedEducationEntity = educationRepository.save(educationEntity);
    return modelMapper.map(updatedEducationEntity, EducationDto.class);
  }

  @Transactional
  @Override
  public void deleteEducation(String userId, long educationId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    EducationEntity educationEntity = educationRepository.findById(educationId);

    if (educationEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.EDUCATION_NOT_FOUND.name(),
          ExceptionErrorMessages.EDUCATION_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }

    if (!educationEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }

    educationRepository.delete(educationEntity);

  }

  @Transactional
  @Override
  public TrainingDto addTraining(String userId, TrainingDto trainingDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    TrainingEntity trainingEntity = modelMapper.map(trainingDto, TrainingEntity.class);
    trainingEntity.setUser(userEntity);
    TrainingEntity createdTrainingEntity = trainingRepository.save(trainingEntity);
    return modelMapper.map(createdTrainingEntity, TrainingDto.class);
  }

  @Transactional
  @Override
  public TrainingDto updateTraining(String userId, long trainingId, TrainingDto trainingDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    TrainingEntity trainingEntity = trainingRepository.findById(trainingId);

    if (trainingEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.TRAINING_NOT_FOUND.name(),
          ExceptionErrorMessages.TRAINING_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (!trainingEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    if (utils.isNonNullAndNonEmpty(trainingDto.getProgram())) {
      trainingEntity.setProgram(trainingDto.getProgram());
    }
    if (utils.isNonNullAndNonEmpty(trainingDto.getInstitutionName())) {
      trainingEntity.setInstitutionName(trainingDto.getInstitutionName());
    }

    if (utils.isNonNull(trainingDto.getStartDate())) {
      trainingEntity.setStartDate(trainingDto.getStartDate());
    }
    if (utils.isNonNull(trainingDto.getEndDate())) {
      trainingEntity.setEndDate(trainingDto.getEndDate());
    }

    TrainingEntity updatedTrainingEntity = trainingRepository.save(trainingEntity);
    return modelMapper.map(updatedTrainingEntity, TrainingDto.class);
  }

  @Transactional
  @Override
  public void deleteTraining(String userId, long trainingId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    TrainingEntity trainingEntity = trainingRepository.findById(trainingId);

    if (trainingEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.TRAINING_NOT_FOUND.name(),
          ExceptionErrorMessages.TRAINING_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }

    if (!trainingEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }

    trainingRepository.delete(trainingEntity);

  }

  @Transactional
  @Override
  public AwardDto addAward(String userId, AwardDto awardDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AwardEntity awardEntity = modelMapper.map(awardDto, AwardEntity.class);
    awardEntity.setUser(userEntity);
    AwardEntity createdAwardEntity = awardRepository.save(awardEntity);
    return modelMapper.map(createdAwardEntity, AwardDto.class);
  }

  @Transactional
  @Override
  public AwardDto updateAward(String userId, long awardId, AwardDto awardDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AwardEntity awardEntity = awardRepository.findById(awardId);

    if (awardEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.AWARD_NOT_FOUND.name(),
          ExceptionErrorMessages.AWARD_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (!awardEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    if (utils.isNonNullAndNonEmpty(awardDto.getName())) {
      awardEntity.setName(awardDto.getName());
    }
    if (utils.isNonNullAndNonEmpty(awardDto.getYear())) {
      awardEntity.setYear(awardDto.getYear());
    }

    AwardEntity updatedAwardEntity = awardRepository.save(awardEntity);
    return modelMapper.map(updatedAwardEntity, AwardDto.class);
  }

  @Transactional
  @Override
  public void deleteAward(String userId, long awardId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AwardEntity awardEntity = awardRepository.findById(awardId);

    if (awardEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.AWARD_NOT_FOUND.name(),
          ExceptionErrorMessages.AWARD_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }

    if (!awardEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }

    awardRepository.delete(awardEntity);

  }

  @Transactional
  @Override
  public ExperienceDto addExperience(String userId, ExperienceDto experienceDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    ExperienceEntity experienceEntity = modelMapper.map(experienceDto, ExperienceEntity.class);
    experienceEntity.setUser(userEntity);
    ExperienceEntity createdExperienceEntity = experienceRepository.save(experienceEntity);
    return modelMapper.map(createdExperienceEntity, ExperienceDto.class);
  }

  @Transactional
  @Override
  public ExperienceDto updateExperience(String userId, long experienceId, ExperienceDto experienceDto) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    ExperienceEntity experienceEntity = experienceRepository.findById(experienceId);

    if (experienceEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.EXPERIENCE_NOT_FOUND.name(),
          ExceptionErrorMessages.EXPERIENCE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (!experienceEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    if (utils.isNonNullAndNonEmpty(experienceDto.getTitle())) {
      experienceEntity.setTitle(experienceDto.getTitle());
    }
    if (utils.isNonNullAndNonEmpty(experienceDto.getOrganization())) {
      experienceEntity.setOrganization(experienceDto.getOrganization());
    }
    if (utils.isNonNullAndNonEmpty(experienceDto.getDivision())) {
      experienceEntity.setDivision(experienceDto.getDivision());
    }
    if (utils.isNonNullAndNonEmpty(experienceDto.getDepartment())) {
      experienceEntity.setDepartment(experienceDto.getDepartment());
    }

    if (utils.isNonNull(experienceDto.getStartDate())) {
      experienceEntity.setStartDate(experienceDto.getStartDate());
    }
    if (utils.isNonNull(experienceDto.getEndDate())) {
      experienceEntity.setEndDate(experienceDto.getEndDate());
    }

    ExperienceEntity updatedExperienceEntity = experienceRepository.save(experienceEntity);
    return modelMapper.map(updatedExperienceEntity, ExperienceDto.class);
  }

  @Transactional
  @Override
  public void deleteExperience(String userId, long experienceId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    ExperienceEntity experienceEntity = experienceRepository.findById(experienceId);

    if (experienceEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.EDUCATION_NOT_FOUND.name(),
          ExceptionErrorMessages.EXPERIENCE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }

    if (!experienceEntity.getUser().getUserId().equals(userEntity.getUserId())) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }

    experienceRepository.delete(experienceEntity);

  }

}
