package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.response.RatingCriteriaRest;
import com.moodsinger.ccrt_clinic.service.RatingCriteriaService;
import com.moodsinger.ccrt_clinic.shared.dto.RatingCriteriaDto;

@RestController
@RequestMapping("rating_criteria")
public class RatingCriteriaController {
  @Autowired
  private RatingCriteriaService ratingCriteriaService;
  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public List<RatingCriteriaRest> retrieveRatingCriteria() {
    List<RatingCriteriaDto> ratingCriteriaDtos = ratingCriteriaService.retrieveRatingCriteria();
    List<RatingCriteriaRest> ratingCriteriaRests = new ArrayList<>();
    for (RatingCriteriaDto ratingCriteriaDto : ratingCriteriaDtos) {
      ratingCriteriaRests.add(modelMapper.map(ratingCriteriaDto, RatingCriteriaRest.class));
    }
    return ratingCriteriaRests;
  }
}
