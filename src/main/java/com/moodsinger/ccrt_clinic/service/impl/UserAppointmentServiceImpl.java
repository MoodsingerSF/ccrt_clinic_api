package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.AppointmentEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.repository.AppointmentRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.UserAppointmentService;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;

@Service
public class UserAppointmentServiceImpl implements UserAppointmentService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AppointmentRepository appointmentRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<AppointmentDto> getUserAppointments(String userId, int page, int limit, AppointmentStatus status,
      Date date) {
    UserEntity user = userRepository.findByUserId(userId);
    if (user == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);

    List<RoleEntity> roleEntities = new ArrayList<>(user.getRoles());
    Role role = roleEntities.get(0).getName();
    Pageable pageable = PageRequest.of(page, limit,
        Sort.by(Direction.ASC, "slotStartTime").and(Sort.by(Direction.ASC, "creationTime")));
    if (role == Role.USER) {
      if (status == null) {
        return processPage(appointmentRepository.findAllByPatientUserIdAndDate(userId, date, pageable));
      } else {
        return processPage(
            appointmentRepository.findAllByPatientUserIdAndDateAndStatus(userId, date, status, pageable));
      }
    } else if (role == Role.DOCTOR) {
      if (status == null) {
        return processPage(appointmentRepository.findAllByDoctorUserIdAndDate(userId, date, pageable));
      } else {
        return processPage(appointmentRepository.findAllByDoctorUserIdAndDateAndStatus(userId, date, status, pageable));
      }
    } else if (role == Role.ADMIN) {
      if (status == null) {
        return processPage(appointmentRepository.findAllByDate(date, pageable));
      } else {
        return processPage(appointmentRepository.findAllByDateAndStatus(date, status, pageable));
      }
    }
    return new ArrayList<>();
  }

  private List<AppointmentDto> processPage(Page<AppointmentEntity> appointmentPage) {
    List<AppointmentEntity> appointmentEntities = appointmentPage.getContent();
    List<AppointmentDto> appointmentDtos = new ArrayList<>();
    for (AppointmentEntity appointmentEntity : appointmentEntities) {
      appointmentDtos.add(modelMapper.map(appointmentEntity, AppointmentDto.class));
    }
    return appointmentDtos;
  }

}
