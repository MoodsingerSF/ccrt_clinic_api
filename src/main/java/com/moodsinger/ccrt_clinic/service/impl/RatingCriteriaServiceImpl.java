package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.RatingCriteriaEntity;
import com.moodsinger.ccrt_clinic.io.repository.RatingCriteriaRepository;
import com.moodsinger.ccrt_clinic.service.RatingCriteriaService;
import com.moodsinger.ccrt_clinic.shared.dto.RatingCriteriaDto;

@Service
public class RatingCriteriaServiceImpl implements RatingCriteriaService {

  @Autowired
  private RatingCriteriaRepository ratingCriteriaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<RatingCriteriaDto> retrieveRatingCriteria() {
    List<RatingCriteriaEntity> ratingCriteriaEntities = ratingCriteriaRepository.findAll();
    List<RatingCriteriaDto> ratingCriteriaDtos = new ArrayList<>();
    for (RatingCriteriaEntity ratingCriteriaEntity : ratingCriteriaEntities) {
      ratingCriteriaDtos.add(modelMapper.map(ratingCriteriaEntity, RatingCriteriaDto.class));
    }
    return ratingCriteriaDtos;
  }

}
