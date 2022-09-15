package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.PrescriptionEntity;

@Repository
public interface PrescriptionRepository extends CrudRepository<PrescriptionEntity, Long> {

}
