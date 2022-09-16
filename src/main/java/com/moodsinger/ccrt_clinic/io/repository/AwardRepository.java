package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.AwardEntity;

@Repository
public interface AwardRepository extends CrudRepository<AwardEntity, Long> {
  AwardEntity findById(long awardId);
}
