package com.moodsinger.ccrt_clinic.io.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.AppointmentEntity;
import com.moodsinger.ccrt_clinic.io.entity.DoctorAppointmentCount;
// import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;

@Repository
public interface AppointmentRepository extends PagingAndSortingRepository<AppointmentEntity, Long> {
  Page<AppointmentEntity> findAllBySlotSlotId(String slotId, Pageable pageable);

  AppointmentEntity findByAppointmentId(String appointmentId);

  Page<AppointmentEntity> findAllByDate(Date date, Pageable pageable);

  Page<AppointmentEntity> findAllByDateAndStatus(Date date, AppointmentStatus status, Pageable pageable);

  Page<AppointmentEntity> findAllByDoctorUserIdAndDate(String userId, Date date,
      Pageable pageable);

  Page<AppointmentEntity> findAllByDoctorUserIdAndDateAndStatus(String userId, Date date,
      AppointmentStatus status,
      Pageable pageable);

  Page<AppointmentEntity> findAllByPatientUserIdAndDate(String userId, Date date,
      Pageable pageable);

  Page<AppointmentEntity> findAllByPatientUserIdAndDateAndStatus(String userId, Date date,
      AppointmentStatus status,
      Pageable pageable);

  @Query("SELECT new com.moodsinger.ccrt_clinic.io.entity.DoctorAppointmentCount(a.doctor.userId,COUNT(*) AS totalAppointments) FROM AppointmentEntity a WHERE status=:status GROUP BY a.doctor ORDER BY COUNT(*) DESC")
  Page<DoctorAppointmentCount> findPopularDoctors(AppointmentStatus status, Pageable pageable);

}
