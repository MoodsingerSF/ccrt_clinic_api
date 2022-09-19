package com.moodsinger.ccrt_clinic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;
import com.moodsinger.ccrt_clinic.io.repository.SpecializationRepository;
import com.moodsinger.ccrt_clinic.service.SpecializationService;

@Service
public class SpecializationServiceImpl implements SpecializationService {

  @Autowired
  private SpecializationRepository specializationRepository;

  @Override
  public SpecializationEntity getOrCreateSpecialization(String specialization) {
    SpecializationEntity foundSpecializationEntity = specializationRepository.findByName(specialization);
    if (foundSpecializationEntity != null)
      return foundSpecializationEntity;
    SpecializationEntity newSpecializationEntity = new SpecializationEntity();
    newSpecializationEntity.setName(specialization);
    SpecializationEntity createdSpecializationEntity = specializationRepository.save(newSpecializationEntity);
    return createdSpecializationEntity;
  }

}
