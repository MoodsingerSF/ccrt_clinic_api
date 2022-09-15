package com.moodsinger.ccrt_clinic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.exceptions.AppointmentServiceException;
import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.AppointmentEntity;
import com.moodsinger.ccrt_clinic.io.entity.AppointmentResourceEntity;
import com.moodsinger.ccrt_clinic.io.entity.DoctorScheduleEntity;
import com.moodsinger.ccrt_clinic.io.entity.MedicationEntity;
import com.moodsinger.ccrt_clinic.io.entity.PatientReportEntity;
import com.moodsinger.ccrt_clinic.io.entity.PrescriptionEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.SlotEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.repository.AppointmentRepository;
import com.moodsinger.ccrt_clinic.io.repository.AppointmentResourceRepository;
import com.moodsinger.ccrt_clinic.io.repository.MedicationRepository;
import com.moodsinger.ccrt_clinic.io.repository.PatientReportRepository;
import com.moodsinger.ccrt_clinic.io.repository.PrescriptionRepository;
import com.moodsinger.ccrt_clinic.io.repository.SlotRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.model.request.AppointmentCancelRequestModel;
import com.moodsinger.ccrt_clinic.model.request.AppointmentEndRequestModel;
import com.moodsinger.ccrt_clinic.service.AppointmentService;
import com.moodsinger.ccrt_clinic.shared.AmazonSES;
import com.moodsinger.ccrt_clinic.shared.FileUploadUtil;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.MedicationDto;
import com.moodsinger.ccrt_clinic.shared.dto.PrescriptionDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  SlotRepository slotRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Autowired
  private AmazonSES amazonSES;

  @Autowired
  private AppointmentResourceRepository appointmentResourceRepository;

  @Autowired
  private PatientReportRepository patientReportRepository;

  @Autowired
  private PrescriptionRepository prescriptionRepository;

  @Autowired
  private MedicationRepository medicationRepository;

  @Transactional
  @Override
  public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
    UserEntity patient = userRepository.findByUserId(appointmentDto.getPatientUserId());
    if (patient == null)
      throw new AppointmentServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    SlotEntity slot = slotRepository.findBySlotId(appointmentDto.getSlotId());
    if (slot == null)
      throw new AppointmentServiceException(ExceptionErrorCodes.SLOT_NOT_FOUND.name(),
          ExceptionErrorMessages.SLOT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    AppointmentEntity appointmentEntity = modelMapper.map(appointmentDto, AppointmentEntity.class);
    appointmentEntity.setAppointmentId(utils.generateAppointmentId());
    appointmentEntity.setFee(500);
    String meetingLink = "https://meet.google.com/new";
    appointmentEntity.setMeetingLink(meetingLink);
    appointmentEntity.setPatient(patient);
    DoctorScheduleEntity doctorScheduleEntity = slot.getDoctorScheduleEntity();
    UserEntity doctor = doctorScheduleEntity.getUser();
    appointmentEntity.setDoctor(doctor);
    appointmentEntity.setSlot(slot);
    String patientVerificationCode = utils.generatePatientVerificationCode();
    appointmentEntity.setPatientVerificationCode(patientVerificationCode);
    appointmentEntity.setStatus(AppointmentStatus.PENDING);
    AppointmentEntity createdAppointmentEntity = appointmentRepository.save(appointmentEntity);

    // transfer the reports from user profile to appointment
    List<PatientReportEntity> patientReportEntities = patientReportRepository
        .findAllByUserUserId(appointmentDto.getPatientUserId());
    List<AppointmentResourceEntity> appointmentResourceEntities = new ArrayList<>();

    for (PatientReportEntity patientReportEntity : patientReportEntities) {
      AppointmentResourceEntity appointmentResourceEntity = modelMapper.map(patientReportEntity,
          AppointmentResourceEntity.class);
      appointmentResourceEntity.setAppointment(createdAppointmentEntity);
      appointmentResourceEntity.setResourceId(utils.generateImageId());
      appointmentResourceEntities.add(appointmentResourceEntity);

    }
    appointmentResourceRepository.saveAll(appointmentResourceEntities);
    amazonSES.sendMeetingLink("miayub@du.ac.bd", "az.islam.rajib@gmail.com",
        patientVerificationCode, meetingLink);
    return modelMapper.map(createdAppointmentEntity, AppointmentDto.class);
  }

  @Override
  public List<AppointmentDto> getAppointments(String slotId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("date").descending());
    List<AppointmentEntity> appointments = appointmentRepository.findAllBySlotSlotId(slotId, pageable).getContent();
    List<AppointmentDto> appointmentDtos = new ArrayList<>();
    for (AppointmentEntity appointmentEntity : appointments) {

      appointmentDtos.add(modelMapper.map(appointmentEntity, AppointmentDto.class));
    }
    return appointmentDtos;
  }

  @Override
  public AppointmentDto getAppointmentDetails(String appointmentId) {
    AppointmentEntity foundAppointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (foundAppointmentEntity == null)
      throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
          ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    return modelMapper.map(foundAppointmentEntity, AppointmentDto.class);
  }

  @Override
  public ResourceDto addResource(String appointmentId, String title, MultipartFile image) {

    try {
      AppointmentEntity appointment = appointmentRepository.findByAppointmentId(appointmentId);
      if (appointment == null) {
        throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
            ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      String resourceId = utils.generateImageId();
      String url = fileUploadUtil.saveFile(
          FileUploadUtil.APPOINTMENT_RESOURCES_UPLOAD_DIR + File.separator + appointmentId,
          resourceId + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      AppointmentResourceEntity appointmentResourceEntity = new AppointmentResourceEntity();
      appointmentResourceEntity.setResourceId(resourceId);
      appointmentResourceEntity.setAppointment(appointment);
      appointmentResourceEntity.setTitle(title);
      appointmentResourceEntity.setImageUrl(url);
      AppointmentResourceEntity createdResourceEntity = appointmentResourceRepository.save(appointmentResourceEntity);
      return modelMapper.map(createdResourceEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new AppointmentServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    }

  }

  @Override
  public AppointmentDto endAppointment(String appointmentId, AppointmentEndRequestModel appointmentEndRequestModel) {
    AppointmentEntity appointment = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointment == null) {
      throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
          ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (appointment.getStatus() == AppointmentStatus.PENDING) {
      if (!appointmentEndRequestModel.getCode().equals(appointment.getPatientVerificationCode())) {
        throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_VERIFICATION_CODE_MISMATCH.name(),
            ExceptionErrorMessages.APPOINTMENT_VERIFICATION_CODE_MISMATCH.getMessage(), HttpStatus.BAD_REQUEST);
      }
      UserEntity user = userRepository.findByUserId(appointmentEndRequestModel.getUserId());
      if (user == null) {
        throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
            ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      List<RoleEntity> roleEntities = new ArrayList<>(user.getRoles());
      Role role = roleEntities.get(0).getName();
      if (!appointment.getDoctor().getUserId().equals(appointmentEndRequestModel.getUserId()) && role != Role.ADMIN) {
        throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
            ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      appointment.setStatus(AppointmentStatus.FINISHED);
      appointment.setCompletionTime(new Date());
      AppointmentEntity updatedAppointmentEntity = appointmentRepository.save(appointment);
      return modelMapper.map(updatedAppointmentEntity, AppointmentDto.class);
    } else {
      throw new AppointmentServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  @Override
  public AppointmentDto cancelAppointment(String appointmentId,
      AppointmentCancelRequestModel appointmentCancelRequestModel) {
    String userId = appointmentCancelRequestModel.getUserId();
    UserEntity user = userRepository.findByUserId(userId);
    if (user == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
          ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RoleEntity> roleEntities = new ArrayList<>(user.getRoles());
    Role role = roleEntities.get(0).getName();
    if (appointmentEntity.getStatus() == AppointmentStatus.PENDING) {
      if (!appointmentEntity.getPatient().getUserId().equals(userId)
          && !appointmentEntity.getDoctor().getUserId().equals(userId) && role != Role.ADMIN) {
        throw new AppointmentServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
            ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      appointmentEntity.setStatus(AppointmentStatus.CANCELLED);
      appointmentEntity.setCancellationTime(new Date());
      AppointmentEntity updatedAppointmentEntity = appointmentRepository.save(appointmentEntity);
      return modelMapper.map(updatedAppointmentEntity, AppointmentDto.class);
    } else {
      throw new AppointmentServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
  }

  @Override
  public List<ResourceDto> getAppointmentResources(String appointmentId) {
    List<AppointmentResourceEntity> appointmentResourceEntities = appointmentResourceRepository
        .findAllByAppointmentAppointmentId(appointmentId);
    List<ResourceDto> resourceDtos = new ArrayList<>();
    for (AppointmentResourceEntity appointmentResourceEntity : appointmentResourceEntities) {
      resourceDtos.add(modelMapper.map(appointmentResourceEntity, ResourceDto.class));
    }
    return resourceDtos;
  }

  @Transactional
  @Override
  public ResourceDto updateResource(String appointmentId, String resourceId, String userId,
      MultipartFile image) {
    try {
      AppointmentResourceEntity appointmentResourceEntity = appointmentResourceRepository.findByResourceId(resourceId);
      if (appointmentResourceEntity == null) {
        throw new AppointmentServiceException(ExceptionErrorCodes.RESOURCE_NOT_FOUND.name(),
            ExceptionErrorMessages.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      AppointmentEntity appointmentEntity = appointmentResourceEntity.getAppointment();
      if (appointmentEntity == null || !appointmentEntity.getAppointmentId().equals(appointmentId)) {
        throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
            ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }

      if (!appointmentEntity.getPatient().getUserId().equals(userId)) {
        throw new AppointmentServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
            ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      String url = fileUploadUtil.saveFile(
          FileUploadUtil.APPOINTMENT_RESOURCES_UPLOAD_DIR + File.separator + appointmentId,
          utils.generateImageId() + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      appointmentResourceEntity.setImageUrl(url);
      AppointmentResourceEntity updatedResourceEntity = appointmentResourceRepository.save(appointmentResourceEntity);
      return modelMapper.map(updatedResourceEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new AppointmentServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    }
  }

  @Transactional
  @Override
  public void createPrescription(String appointmentId, PrescriptionDto prescriptionDto) {
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
          ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (appointmentEntity.getPrescription() == null) {
      PrescriptionEntity prescriptionEntity = modelMapper.map(prescriptionDto, PrescriptionEntity.class);
      prescriptionEntity.setAppointment(appointmentEntity);
      prescriptionEntity.setPrescriptionId(utils.generatePrescriptionId());
      PrescriptionEntity createdPrescriptionEntity = prescriptionRepository.save(prescriptionEntity);
      appointmentEntity.setPrescription(createdPrescriptionEntity);
      appointmentRepository.save(appointmentEntity);
      List<MedicationEntity> medicationEntities = new ArrayList<>();
      for (MedicationDto medicationDto : prescriptionDto.getMedications()) {
        MedicationEntity medicationEntity = modelMapper.map(medicationDto,
            MedicationEntity.class);
        medicationEntity.setPrescription(createdPrescriptionEntity);
        medicationEntities.add(medicationEntity);
      }
      medicationRepository.saveAll(medicationEntities);
    } else {
      throw new AppointmentServiceException(ExceptionErrorCodes.PRESCRIPTION_ALREADY_EXISTS.name(),
          ExceptionErrorMessages.PRESCRIPTION_ALREADY_EXISTS.getMessage(), HttpStatus.FORBIDDEN);
    }

  }

  @Transactional
  @Override
  public PrescriptionDto retrievePrescription(String appointmentId) {
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(ExceptionErrorCodes.APPOINTMENT_NOT_FOUND.name(),
          ExceptionErrorMessages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    PrescriptionEntity prescriptionEntity = appointmentEntity.getPrescription();
    PrescriptionDto prescriptionDto = modelMapper.map(prescriptionEntity, PrescriptionDto.class);

    prescriptionDto.setDate(appointmentEntity.getDate());
    prescriptionDto.setPatient(modelMapper.map(appointmentEntity.getPatient(), UserDto.class));
    prescriptionDto.setDoctor(modelMapper.map(appointmentEntity.getDoctor(), UserDto.class));
    return prescriptionDto;
  }

}
