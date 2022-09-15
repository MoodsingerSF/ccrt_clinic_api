package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.AppointmentResourceEntity;

@Repository
public interface AppointmentResourceRepository extends CrudRepository<AppointmentResourceEntity, Long> {
  List<AppointmentResourceEntity> findAllByAppointmentAppointmentId(String appointmentId);

  AppointmentResourceEntity findByResourceId(String resourceId);

}
