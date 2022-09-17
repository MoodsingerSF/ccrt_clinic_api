package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.RatingCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.RatingAndCriteriaList;
import com.moodsinger.ccrt_clinic.model.response.RatingCriteriaRest;
import com.moodsinger.ccrt_clinic.model.response.RatingRest;
import com.moodsinger.ccrt_clinic.service.RatingCriteriaService;
import com.moodsinger.ccrt_clinic.service.RatingService;
import com.moodsinger.ccrt_clinic.shared.dto.RatingCriteriaDto;
import com.moodsinger.ccrt_clinic.shared.dto.RatingDto;

@RestController
@RequestMapping("/users/{userId}/rating")
public class RatingController {

  @Autowired
  private RatingService ratingService;

  @Autowired
  private RatingCriteriaService ratingCriteriaService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  private List<RatingRest> addRating(@PathVariable(name = "userId") String doctorUserId,
      @RequestBody RatingCreationRequestModel ratingCreationRequestModel) {
    List<RatingDto> ratingDtos = ratingService.addRatings(doctorUserId, ratingCreationRequestModel);
    List<RatingRest> ratingRests = new ArrayList<>();
    for (RatingDto ratingDto : ratingDtos) {
      ratingRests.add(modelMapper.map(ratingDto, RatingRest.class));
    }
    return ratingRests;

  }

  @GetMapping
  public RatingAndCriteriaList retrieveRating(@PathVariable(name = "userId") String doctorUserId,
      @RequestParam(name = "rater", required = false) String raterUserId) {
    List<RatingDto> ratingDtos;
    if (raterUserId != null)
      ratingDtos = ratingService.retrieveRatings(doctorUserId, raterUserId);
    else {
      ratingDtos = ratingService.retrieveAverageRatings(doctorUserId);
    }
    List<RatingRest> ratingRests = new ArrayList<>();
    for (RatingDto ratingDto : ratingDtos) {
      ratingRests.add(modelMapper.map(ratingDto, RatingRest.class));
    }
    RatingAndCriteriaList ratingAndCriteriaList = new RatingAndCriteriaList();
    ratingAndCriteriaList.setRatings(ratingRests);
    List<RatingCriteriaDto> ratingCriteriaDtos = ratingCriteriaService.retrieveRatingCriteria();
    List<RatingCriteriaRest> ratingCriteriaRests = new ArrayList<>();
    for (RatingCriteriaDto ratingCriteriaDto : ratingCriteriaDtos) {
      ratingCriteriaRests.add(modelMapper.map(ratingCriteriaDto, RatingCriteriaRest.class));
    }
    ratingAndCriteriaList.setCriteria(ratingCriteriaRests);
    return ratingAndCriteriaList;
  }

  @PutMapping
  private List<RatingRest> updateRating(@PathVariable(name = "userId") String doctorUserId,
      @RequestBody RatingCreationRequestModel ratingCreationRequestModel) {
    List<RatingDto> ratingDtos = ratingService.updateRatings(doctorUserId, ratingCreationRequestModel);
    List<RatingRest> ratingRests = new ArrayList<>();
    for (RatingDto ratingDto : ratingDtos) {
      ratingRests.add(modelMapper.map(ratingDto, RatingRest.class));
    }
    return ratingRests;

  }

}
