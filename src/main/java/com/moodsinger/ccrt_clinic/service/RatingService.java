package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.model.request.RatingCreationRequestModel;
import com.moodsinger.ccrt_clinic.shared.dto.RatingDto;

public interface RatingService {
  List<RatingDto> addRatings(String doctorUserId, RatingCreationRequestModel ratingCreationRequestModel);

  List<RatingDto> updateRatings(String doctorUserId, RatingCreationRequestModel ratingCreationRequestModel);

  List<RatingDto> retrieveRatings(String doctorUserId, String raterUserId);

  List<RatingDto> retrieveAverageRatings(String doctorUserId);

}
