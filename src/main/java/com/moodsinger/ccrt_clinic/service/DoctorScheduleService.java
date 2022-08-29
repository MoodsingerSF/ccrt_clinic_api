package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.model.response.SlotListRest;
import com.moodsinger.ccrt_clinic.shared.dto.DoctorScheduleDto;
import com.moodsinger.ccrt_clinic.shared.dto.SlotDto;

public interface DoctorScheduleService {
  public void createDoctorSchedule(DoctorScheduleDto doctorScheduleDto);

  public void initialize(UserEntity userEntity);

  public List<SlotDto> addSlot(SlotDto slotDto);

  public SlotListRest retrieveSlots(String doctorUserId);

  public SlotListRest retrieveActiveSlots(String doctorUserId);

  public SlotDto updateSlotVisibility(String userId, String slotId, SlotDto slotDto);

}
