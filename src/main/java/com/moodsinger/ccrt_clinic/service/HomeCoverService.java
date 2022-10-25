package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.enums.VisibilityType;
import com.moodsinger.ccrt_clinic.shared.dto.HomeCoverDto;

public interface HomeCoverService {
  HomeCoverDto addNewHomeCover(HomeCoverDto homeCoverDto);

  List<HomeCoverDto> retrieveHomeCovers(VisibilityType visibilityType);

  void deleteCover(String coverId);

  HomeCoverDto updateHomeCover(String coverId, HomeCoverDto homeCoverDto);

}
