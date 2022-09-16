package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.TrainingEntity;

@Repository
public interface TrainingRepository extends CrudRepository<TrainingEntity, Long> {
  TrainingEntity findById(long trainingId);
}
