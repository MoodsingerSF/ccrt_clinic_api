package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.enums.SortType;
import com.moodsinger.ccrt_clinic.io.enums.SortingCriteria;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.BlogVerificationStatusUpdateRequestModel;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;

public interface BlogService {
	BlogDto createBlog(BlogDto blogDto);

	List<BlogDto> getBlogs(int page, int limit, VerificationStatus verificationStatus,
			SortingCriteria sortingCriteria,
			SortType sortType);

	List<BlogDto> getBlogs(int page, int limit, String tag, VerificationStatus verificationStatus,
			SortingCriteria sortingCriteria, SortType sortType);

	BlogDto getBlog(String blogId);

	BlogDto updateBlog(String blogId, BlogDto blogDetails);

	void deleteBlog(String blogId);

	List<BlogDto> getRelatedBlogs(String blogId, int page, int limit);

	BlogDto updateBlogVerificationStatus(String blogId,
			BlogVerificationStatusUpdateRequestModel blogVerificationStatusUpdateRequestModel);

	long getBlogCount(VerificationStatus verificationStatus);

	BlogDto updateNumberOfTimesRead(String blogId);

	List<BlogDto> searchBlogsByTitleAndTags(String keyword, int page, int limit);

}
