package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.ExperienceEntity;

@Repository
public interface ExperienceRepository extends CrudRepository<ExperienceEntity, Long> {
  ExperienceEntity findById(long experienceId);
}
