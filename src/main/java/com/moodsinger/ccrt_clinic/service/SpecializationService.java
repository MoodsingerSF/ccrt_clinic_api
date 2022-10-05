package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;
import com.moodsinger.ccrt_clinic.shared.dto.SpecializationDto;

public interface SpecializationService {
  SpecializationEntity getOrCreateSpecialization(String specialization);

  List<SpecializationDto> searchByPrefix(String prefix, int page, int limit);

  List<SpecializationDto> retrievePopularSpecializations(int page, int limit);

  List<SpecializationDto> retrieveSpecializations(int page, int limit);

}
