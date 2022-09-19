package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;

@Repository
public interface SpecializationRepository extends PagingAndSortingRepository<SpecializationEntity, Long> {
  SpecializationEntity findByName(String name);
}
