package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;

public interface SpecializationService {
  SpecializationEntity getOrCreateSpecialization(String specialization);
}
