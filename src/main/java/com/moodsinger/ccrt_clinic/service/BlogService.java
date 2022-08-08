package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.BlogVerificationStatusUpdateRequestModel;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;

public interface BlogService {
  BlogDto createBlog(BlogDto blogDto);

  List<BlogDto> getBlogs(int page, int limit, VerificationStatus verificationStatus);

  List<BlogDto> getBlogs(int page, int limit, String tag);

  BlogDto getBlog(String blogId);

  BlogDto updateBlog(String blogId, BlogDto blogDetails);

  void deleteBlog(String blogId);

  List<BlogDto> getRelatedBlogs(String blogId, int page, int limit);

  BlogDto updateBlogVerificationStatus(String blogId,
      BlogVerificationStatusUpdateRequestModel blogVerificationStatusUpdateRequestModel);
}
