package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.MedicationEntity;

@Repository
public interface MedicationRepository extends CrudRepository<MedicationEntity, Long> {

}
