package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.DoctorScheduleServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.DayEntity;
// import com.moodsinger.ccrt_clinic.exceptions.DoctorScheduleServiceException;
// import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
// import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.DoctorScheduleEntity;
import com.moodsinger.ccrt_clinic.io.entity.SlotEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.repository.DayRepository;
import com.moodsinger.ccrt_clinic.io.repository.DoctorScheduleRepository;
import com.moodsinger.ccrt_clinic.io.repository.SlotRepository;
import com.moodsinger.ccrt_clinic.model.response.SlotListRest;
import com.moodsinger.ccrt_clinic.model.response.SlotRest;
import com.moodsinger.ccrt_clinic.service.DoctorScheduleService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.DoctorScheduleDto;
import com.moodsinger.ccrt_clinic.shared.dto.SlotDto;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private DoctorScheduleRepository doctorScheduleRepository;

  @Autowired
  private DayRepository dayRepository;

  @Autowired
  private SlotRepository slotRepository;

  @Autowired
  private Utils utils;

  private final int GENERATED_ID_LENGTH = 20;

  @Override
  public void createDoctorSchedule(DoctorScheduleDto doctorScheduleDto) {
    DoctorScheduleEntity doctorScheduleEntity = modelMapper.map(doctorScheduleDto, DoctorScheduleEntity.class);
    doctorScheduleEntity.setDoctorScheduleId(utils.generateDoctorScheduleId(GENERATED_ID_LENGTH));
    doctorScheduleRepository.save(doctorScheduleEntity);
    // if (createdDoctorScheduleEntity == null) {
    // throw new
    // DoctorScheduleServiceException(ExceptionErrorCodes.DOCTOR_SCHEDULE_CREATION_ERROR.name(),
    // ExceptionErrorMessages.DOCTOR_SCHEDULE_CREATION_ERROR.getMessage());
    // }
  }

  @Transactional
  @Override
  public void initialize(UserEntity userEntity) {
    List<DayEntity> days = dayRepository.findAll();
    List<DoctorScheduleEntity> doctorScheduleEntities = new ArrayList<>();
    for (DayEntity day : days) {
      DoctorScheduleEntity doctorScheduleEntity = new DoctorScheduleEntity();
      doctorScheduleEntity.setDoctorScheduleId(utils.generateDoctorScheduleId(GENERATED_ID_LENGTH));
      doctorScheduleEntity.setDay(day);
      doctorScheduleEntity.setUser(userEntity);
      doctorScheduleEntities.add(doctorScheduleEntity);
    }
    doctorScheduleRepository.saveAll(doctorScheduleEntities);
  }

  @Override
  public List<SlotDto> addSlot(SlotDto slotDto) {
    // System.out.println(slotDto);
    DoctorScheduleEntity doctorScheduleEntity = doctorScheduleRepository.findByUserUserIdAndDayCode(slotDto.getUserId(),
        slotDto.getDayCode());
    if (doctorScheduleEntity == null) {
      throw new DoctorScheduleServiceException(MessageCodes.SCHEDULE_NOT_FOUND.name(),
          Messages.SCHEDULE_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }

    List<SlotEntity> activeSlots = slotRepository
        .findAllByDoctorScheduleEntityDoctorScheduleIdAndIsEnabled(doctorScheduleEntity.getDoctorScheduleId(), true);
    SlotEntity slotEntity = modelMapper.map(slotDto, SlotEntity.class);

    slotEntity.setSlotId(utils.generateSlotId(30));
    slotEntity.setDoctorScheduleEntity(doctorScheduleEntity);
    slotEntity.setEnabled(true);
    if (isOverlapping(activeSlots, slotEntity)) {
      throw new DoctorScheduleServiceException(MessageCodes.OVERLAPPING_SLOT.name(),
          Messages.OVERLAPPING_SLOT.getMessage(), HttpStatus.BAD_REQUEST);
    }

    SlotEntity newlyCreatedSlot = slotRepository.save(slotEntity);
    if (newlyCreatedSlot == null) {
      throw new DoctorScheduleServiceException(MessageCodes.SLOT_NOT_CREATED.name(),
          Messages.SLOT_NOT_CREATED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    List<SlotEntity> inActiveSlots = slotRepository
        .findAllByDoctorScheduleEntityDoctorScheduleIdAndIsEnabled(doctorScheduleEntity.getDoctorScheduleId(), false);
    activeSlots.add(slotRepository.findBySlotId(newlyCreatedSlot.getSlotId()));
    Collections.sort(activeSlots, compareByStartTime);
    List<SlotDto> newSlots = new ArrayList<>();
    for (SlotEntity slot : activeSlots) {
      newSlots.add(modelMapper.map(slot, SlotDto.class));
    }
    for (SlotEntity slot : inActiveSlots) {
      newSlots.add(modelMapper.map(slot, SlotDto.class));
    }
    Collections.sort(newSlots, compareSlotDtosByStartTime);
    return newSlots;
  }

  @Transactional
  @Override
  public SlotListRest retrieveSlots(String doctorUserId) {
    SlotListRest slotListRest = new SlotListRest();
    List<SlotEntity> allSlots = slotRepository.findAllByDoctorScheduleEntityUserUserId(doctorUserId);
    for (SlotEntity slot : allSlots) {
      slotListRest.add(slot.getDoctorScheduleEntity().getDay().getCode(), modelMapper.map(slot, SlotRest.class));
    }
    slotListRest.sort();
    return slotListRest;
  }

  @Override
  public SlotDto updateSlotVisibility(String userId, String slotId, SlotDto slotDto) {
    SlotEntity slotEntity = slotRepository.findBySlotId(slotId);
    if (slotEntity == null) {
      throw new DoctorScheduleServiceException(MessageCodes.SLOT_NOT_FOUND.name(),
          Messages.SLOT_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    slotEntity.setEnabled(slotDto.isEnabled());
    if (slotDto.isEnabled()) {
      List<SlotEntity> activeSlots = slotRepository
          .findAllByDoctorScheduleEntityDoctorScheduleIdAndIsEnabled(
              slotEntity.getDoctorScheduleEntity().getDoctorScheduleId(), true);
      if (isOverlapping(activeSlots, slotEntity)) {
        throw new DoctorScheduleServiceException(MessageCodes.OVERLAPPING_SLOT.name(),
            Messages.OVERLAPPING_SLOT.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }
    SlotEntity updatedSlotEntity = slotRepository.save(slotEntity);
    return modelMapper.map(updatedSlotEntity, SlotDto.class);
  }

  @Transactional
  @Override
  public SlotListRest retrieveActiveSlots(String doctorUserId) {
    SlotListRest slotListRest = new SlotListRest();
    List<SlotEntity> allSlots = slotRepository.findAllByDoctorScheduleEntityUserUserIdAndIsEnabled(doctorUserId, true);
    for (SlotEntity slot : allSlots) {
      slotListRest.add(slot.getDoctorScheduleEntity().getDay().getCode(), modelMapper.map(slot, SlotRest.class));
    }
    slotListRest.sort();
    return slotListRest;
  }

  Comparator<SlotEntity> compareByStartTime = (SlotEntity a, SlotEntity b) -> a.getStartTime()
      .compareTo(b.getStartTime());
  Comparator<SlotDto> compareSlotDtosByStartTime = (SlotDto a, SlotDto b) -> a.getStartTime()
      .compareTo(b.getStartTime());

  private boolean isOverlapping(List<SlotEntity> activeSlots, SlotEntity slotEntity) {

    Collections.sort(activeSlots, compareByStartTime);
    for (SlotEntity slot : activeSlots) {
      if ((slotEntity.getStartTime().compareTo(slot.getStartTime()) > 0
          && slotEntity.getStartTime().compareTo(slot.getEndTime()) < 0)
          || (slotEntity.getEndTime().compareTo(slot.getStartTime()) > 0
              && slotEntity.getEndTime().compareTo(slot.getEndTime()) < 0)) {
        return true;
      }
    }
    return false;
  }

}
