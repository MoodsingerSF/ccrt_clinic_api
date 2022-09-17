package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.RatingCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.RatingRest;
import com.moodsinger.ccrt_clinic.service.RatingService;
import com.moodsinger.ccrt_clinic.shared.dto.RatingDto;

@RestController
@RequestMapping("users/{userId}/rating")
public class RatingController {

  @Autowired
  private RatingService ratingService;

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

}
