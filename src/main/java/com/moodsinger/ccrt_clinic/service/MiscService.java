package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

public interface MiscService {
  List<UserDto> findPopularDoctors(int page, int limit, int specializationId);

  List<UserDto> searchDoctors(int page, int limit, String keyword);

  List<BlogDto> searchBlogs(int page, int limit, String keyword);

}
