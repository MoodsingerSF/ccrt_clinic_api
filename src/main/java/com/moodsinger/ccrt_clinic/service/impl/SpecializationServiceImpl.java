package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.PopularSpecialization;
import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;
import com.moodsinger.ccrt_clinic.io.repository.SpecializationRepository;
import com.moodsinger.ccrt_clinic.service.SpecializationService;
import com.moodsinger.ccrt_clinic.shared.dto.SpecializationDto;

@Service
public class SpecializationServiceImpl implements SpecializationService {

  @Autowired
  private SpecializationRepository specializationRepository;

  @Autowired
  private ModelMapper modelMapper;

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

  @Override
  public List<SpecializationDto> searchByPrefix(String prefix, int page, int limit) {

    Page<SpecializationEntity> foundSpecializationEntitiesPage = specializationRepository
        .findAllByNameStartsWithIgnoreCase(prefix, PageRequest.of(page, limit, Sort.by("name").ascending()));
    return processPage(foundSpecializationEntitiesPage);

  }

  @Override
  public List<SpecializationDto> retrievePopularSpecializations(int page, int limit) {
    Page<PopularSpecialization> foundSpecializationEntitiesPage = specializationRepository
        .findPopularSpecializations(PageRequest.of(page, limit));
    List<PopularSpecialization> foundSpecializationEntities = foundSpecializationEntitiesPage.getContent();
    List<SpecializationDto> specializationDtos = new ArrayList<>();
    for (PopularSpecialization popularSpecialization : foundSpecializationEntities) {
      specializationDtos.add(modelMapper.map(popularSpecialization, SpecializationDto.class));
    }
    return specializationDtos;
  }

  private List<SpecializationDto> processPage(Page<SpecializationEntity> page) {
    List<SpecializationEntity> foundSpecializationEntities = page.getContent();
    List<SpecializationDto> specializationDtos = new ArrayList<>();
    for (SpecializationEntity specializationEntity : foundSpecializationEntities) {
      specializationDtos.add(modelMapper.map(specializationEntity, SpecializationDto.class));
    }
    return specializationDtos;
  }

}
