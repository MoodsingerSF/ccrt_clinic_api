package com.moodsinger.ccrt_clinic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
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
import com.moodsinger.ccrt_clinic.service.FeeService;
import com.moodsinger.ccrt_clinic.shared.AmazonSES;
import com.moodsinger.ccrt_clinic.shared.FileUploadUtil;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.MedicationDto;
import com.moodsinger.ccrt_clinic.shared.dto.PrescriptionDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;

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

  @Autowired
  private FeeService feeService;

  @Transactional
  @Override
  public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
    UserEntity patient = userRepository.findByUserId(appointmentDto.getPatientUserId());
    if (patient == null)
      throw new AppointmentServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    SlotEntity slot = slotRepository.findBySlotId(appointmentDto.getSlotId());
    if (slot == null)
      throw new AppointmentServiceException(MessageCodes.SLOT_NOT_FOUND.name(),
          Messages.SLOT_NOT_FOUND.getMessage(), HttpStatus.FORBIDDEN);
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dhaka"));
    Date currentDate = calendar.getTime();
    AppointmentEntity foundAppointmentEntity = appointmentRepository.findBySlotSlotIdAndDate(slot.getSlotId(),
        currentDate);
    if (foundAppointmentEntity != null) {
      throw new AppointmentServiceException(MessageCodes.SLOT_BOOKED.name(),
          Messages.SLOT_BOOKED.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AppointmentEntity appointmentEntity = modelMapper.map(appointmentDto, AppointmentEntity.class);
    appointmentEntity.setAppointmentId(utils.generateAppointmentId());
    String meetingLink = "https://meet.google.com/new";
    appointmentEntity.setMeetingLink(meetingLink);
    appointmentEntity.setPatient(patient);
    DoctorScheduleEntity doctorScheduleEntity = slot.getDoctorScheduleEntity();
    UserEntity doctor = doctorScheduleEntity.getUser();
    appointmentEntity.setDoctor(doctor);
    appointmentEntity.setFee(feeService.getFeeOfDoctor(doctor.getUserId()));
    appointmentEntity.setSlot(slot);
    String patientVerificationCode = utils.generatePatientVerificationCode();
    appointmentEntity.setPatientVerificationCode(patientVerificationCode);
    appointmentEntity.setCodeForPrescriptionViewForPatient(utils.generateCodeForPrescriptionView());
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
    // amazonSES.sendMeetingLink(doctor.getEmail(), patient.getEmail(),
    // patientVerificationCode, meetingLink);

    amazonSES.sendMeetingLink("rafi1017623150@gmail.com", "miayub@du.ac.bd",
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
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    return modelMapper.map(foundAppointmentEntity, AppointmentDto.class);
  }

  @Override
  public ResourceDto addResource(String appointmentId, String title, MultipartFile image) {

    try {
      AppointmentEntity appointment = appointmentRepository.findByAppointmentId(appointmentId);
      if (appointment == null) {
        throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
            Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      String resourceId = utils.generateImageId();
      fileUploadUtil.saveFile(
          FileUploadUtil.APPOINTMENT_RESOURCES_UPLOAD_DIR + File.separator + appointmentId,
          resourceId + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      AppointmentResourceEntity appointmentResourceEntity = new AppointmentResourceEntity();
      appointmentResourceEntity.setResourceId(resourceId);
      appointmentResourceEntity.setAppointment(appointment);
      appointmentResourceEntity.setTitle(title);
      appointmentResourceEntity.setImageUrl("appointments/" + appointmentId + "/" + resourceId + "."
          + utils.getFileExtension(image.getOriginalFilename()));
      AppointmentResourceEntity createdResourceEntity = appointmentResourceRepository.save(appointmentResourceEntity);
      return modelMapper.map(createdResourceEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new AppointmentServiceException(MessageCodes.FILE_SAVE_ERROR.name(),
          Messages.FILE_SAVE_ERROR.getMessage());
    }

  }

  @Override
  public AppointmentDto endAppointment(String appointmentId, AppointmentEndRequestModel appointmentEndRequestModel) {
    AppointmentEntity appointment = appointmentRepository.findByAppointmentId(appointmentId);

    if (appointment == null) {
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (appointment.getStatus() == AppointmentStatus.PENDING) {
      if (appointment.getPrescription() == null) {
        throw new AppointmentServiceException(MessageCodes.PRESCRIPTION_NOT_ADDED.name(),
            Messages.PRESCRIPTION_NOT_ADDED.getMessage(), HttpStatus.BAD_REQUEST);
      }
      if (!appointmentEndRequestModel.getCode().equals(appointment.getPatientVerificationCode())) {
        throw new AppointmentServiceException(MessageCodes.APPOINTMENT_VERIFICATION_CODE_MISMATCH.name(),
            Messages.APPOINTMENT_VERIFICATION_CODE_MISMATCH.getMessage(), HttpStatus.BAD_REQUEST);
      }
      UserEntity user = userRepository.findByUserId(appointmentEndRequestModel.getUserId());
      if (user == null) {
        throw new UserServiceException(MessageCodes.USER_NOT_FOUND.name(),
            Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      List<RoleEntity> roleEntities = new ArrayList<>(user.getRoles());
      Role role = roleEntities.get(0).getName();
      UserEntity doctor = appointment.getDoctor();
      UserEntity patient = appointment.getPatient();
      if (!doctor.getUserId().equals(appointmentEndRequestModel.getUserId()) && role != Role.ADMIN) {
        throw new UserServiceException(MessageCodes.FORBIDDEN.name(),
            Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      appointment.setStatus(AppointmentStatus.FINISHED);
      appointment.setCompletionTime(new Date());
      AppointmentEntity updatedAppointmentEntity = appointmentRepository.save(appointment);
      amazonSES.sendPrescriptionViewCode(patient.getEmail(), patient.getFirstName() + " " + patient.getLastName(),
          appointment.getCodeForPrescriptionViewForPatient());
      return modelMapper.map(updatedAppointmentEntity, AppointmentDto.class);
    } else {
      throw new AppointmentServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  @Override
  public AppointmentDto cancelAppointment(String appointmentId,
      AppointmentCancelRequestModel appointmentCancelRequestModel) {
    String userId = appointmentCancelRequestModel.getUserId();
    UserEntity user = userRepository.findByUserId(userId);
    if (user == null) {
      throw new UserServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RoleEntity> roleEntities = new ArrayList<>(user.getRoles());
    Role role = roleEntities.get(0).getName();
    if (appointmentEntity.getStatus() == AppointmentStatus.PENDING) {
      if (!appointmentEntity.getPatient().getUserId().equals(userId)
          && !appointmentEntity.getDoctor().getUserId().equals(userId) && role != Role.ADMIN) {
        throw new AppointmentServiceException(MessageCodes.FORBIDDEN.name(),
            Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      // if patient, he/she will be able to cancel
      appointmentEntity.setStatus(AppointmentStatus.CANCELLED);
      appointmentEntity.setCancellationTime(new Date());
      // send money back to patient
      AppointmentEntity updatedAppointmentEntity = appointmentRepository.save(appointmentEntity);
      amazonSES.sendAppointmentCancellationEmail(modelMapper.map(updatedAppointmentEntity, AppointmentDto.class));
      return modelMapper.map(updatedAppointmentEntity, AppointmentDto.class);
    } else {
      throw new AppointmentServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
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
        throw new AppointmentServiceException(MessageCodes.RESOURCE_NOT_FOUND.name(),
            Messages.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }
      AppointmentEntity appointmentEntity = appointmentResourceEntity.getAppointment();
      if (appointmentEntity == null || !appointmentEntity.getAppointmentId().equals(appointmentId)) {
        throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
            Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      }

      if (!appointmentEntity.getPatient().getUserId().equals(userId)) {
        throw new AppointmentServiceException(MessageCodes.FORBIDDEN.name(),
            Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
      }
      String imageId = utils.generateImageId();
      fileUploadUtil.saveFile(
          FileUploadUtil.APPOINTMENT_RESOURCES_UPLOAD_DIR + File.separator + appointmentId,
          imageId + "." + utils.getFileExtension(image.getOriginalFilename()), image);
      appointmentResourceEntity.setImageUrl(
          "appointments/" + appointmentId + "/" + imageId + "." + utils.getFileExtension(image.getOriginalFilename()));
      AppointmentResourceEntity updatedResourceEntity = appointmentResourceRepository.save(appointmentResourceEntity);
      return modelMapper.map(updatedResourceEntity, ResourceDto.class);

    } catch (IOException e) {
      throw new AppointmentServiceException(MessageCodes.FILE_SAVE_ERROR.name(),
          Messages.FILE_SAVE_ERROR.getMessage());
    }
  }

  @Transactional
  @Override
  public void createPrescription(String appointmentId, PrescriptionDto prescriptionDto) {
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (appointmentEntity.getPrescription() == null) {
      List<MedicationDto> medicationDtos = prescriptionDto.getMedications();
      if (medicationDtos == null || medicationDtos.size() == 0) {
        throw new AppointmentServiceException(MessageCodes.PRESCRIPTION_EMPTY.name(),
            Messages.PRESCRIPTION_ALREADY_EXISTS.getMessage(), HttpStatus.FORBIDDEN);
      }
      PrescriptionEntity prescriptionEntity = modelMapper.map(prescriptionDto, PrescriptionEntity.class);
      prescriptionEntity.setAppointment(appointmentEntity);
      prescriptionEntity.setPrescriptionId(utils.generatePrescriptionId());
      PrescriptionEntity createdPrescriptionEntity = prescriptionRepository.save(prescriptionEntity);
      appointmentEntity.setPrescription(createdPrescriptionEntity);
      appointmentRepository.save(appointmentEntity);
      List<MedicationEntity> medicationEntities = new ArrayList<>();
      for (MedicationDto medicationDto : medicationDtos) {
        MedicationEntity medicationEntity = modelMapper.map(medicationDto,
            MedicationEntity.class);

        medicationEntity.setPrescription(createdPrescriptionEntity);
        medicationEntities.add(medicationEntity);
      }
      medicationRepository.saveAll(medicationEntities);
    } else {
      throw new AppointmentServiceException(MessageCodes.PRESCRIPTION_ALREADY_EXISTS.name(),
          Messages.PRESCRIPTION_ALREADY_EXISTS.getMessage(), HttpStatus.FORBIDDEN);
    }

  }

  @Transactional
  @Override
  public PrescriptionDto retrievePrescription(String appointmentId) {
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentId);
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    PrescriptionEntity prescriptionEntity = appointmentEntity.getPrescription();
    if (prescriptionEntity == null) {
      throw new AppointmentServiceException(MessageCodes.PRESCRIPTION_NOT_FOUND.name(),
          Messages.PRESCRIPTION_NOT_FOUND.getMessage(), HttpStatus.NO_CONTENT);
    }
    PrescriptionDto prescriptionDto = modelMapper.map(prescriptionEntity, PrescriptionDto.class);

    prescriptionDto.setDate(appointmentEntity.getDate());
    // prescriptionDto.setPatient(modelMapper.map(appointmentEntity.getPatient(),
    // UserDto.class));
    // prescriptionDto.setDoctor(modelMapper.map(appointmentEntity.getDoctor(),
    // UserDto.class));
    return prescriptionDto;
  }

  @Override
  public void validatePrescriptionViewCode(AppointmentDto appointmentDto) {
    AppointmentEntity appointmentEntity = appointmentRepository.findByAppointmentId(appointmentDto.getAppointmentId());
    if (appointmentEntity == null) {
      throw new AppointmentServiceException(MessageCodes.APPOINTMENT_NOT_FOUND.name(),
          Messages.APPOINTMENT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    if (!appointmentDto.getCodeForPrescriptionViewForPatient()
        .equals(appointmentEntity.getCodeForPrescriptionViewForPatient())) {
      throw new AppointmentServiceException(MessageCodes.PRESCRIPTION_VIEW_CODE_MISMATCH.name(),
          Messages.PRESCRIPTION_VIEW_CODE_MISMATCH.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

}
