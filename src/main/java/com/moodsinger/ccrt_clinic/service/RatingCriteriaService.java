package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.shared.dto.RatingCriteriaDto;

public interface RatingCriteriaService {
  List<RatingCriteriaDto> retrieveRatingCriteria();
}
