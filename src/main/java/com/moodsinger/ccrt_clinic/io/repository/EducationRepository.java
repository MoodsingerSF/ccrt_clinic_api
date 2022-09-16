package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.EducationEntity;

@Repository
public interface EducationRepository extends CrudRepository<EducationEntity, Long> {
  EducationEntity findById(long educationId);
}
