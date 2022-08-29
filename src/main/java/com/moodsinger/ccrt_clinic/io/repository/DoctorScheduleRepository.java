package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.DoctorScheduleEntity;

@Repository
public interface DoctorScheduleRepository extends CrudRepository<DoctorScheduleEntity, Long> {
  DoctorScheduleEntity findByUserUserIdAndDayCode(String userId, String code);
}
