package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;

public interface UserBlogService {
  public List<BlogDto> getUserBlogs(String userId, int page, int limit);
}
