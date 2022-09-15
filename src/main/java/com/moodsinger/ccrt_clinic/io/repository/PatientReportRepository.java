package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.PatientReportEntity;

@Repository
public interface PatientReportRepository extends CrudRepository<PatientReportEntity, Long> {
  List<PatientReportEntity> findAllByUserUserId(String userId);

  PatientReportEntity findByResourceId(String resourceId);
}
