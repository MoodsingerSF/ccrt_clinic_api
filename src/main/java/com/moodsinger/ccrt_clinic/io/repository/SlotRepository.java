package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.SlotEntity;

@Repository
public interface SlotRepository extends CrudRepository<SlotEntity, Long> {
  List<SlotEntity> findAllByDoctorScheduleEntityDoctorScheduleIdAndIsEnabled(String doctorScheduleId,
      boolean isEnabled);

  List<SlotEntity> findAllByDoctorScheduleEntityUserUserId(String doctorUserId);

  List<SlotEntity> findAllByDoctorScheduleEntityUserUserIdAndIsEnabled(String doctorUserId, boolean isEnabled);

  SlotEntity findBySlotId(String slotId);
}
