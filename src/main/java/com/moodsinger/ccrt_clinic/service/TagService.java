package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.entity.TagEntity;
import com.moodsinger.ccrt_clinic.model.response.BlogCountByTagRest;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

public interface TagService {
  TagDto createTag(TagDto tagDto);

  TagEntity getOrCreateTag(TagDto tagDto);

  List<TagDto> searchTagsByPrefix(String prefix, int page, int limit);

  List<BlogCountByTagRest> retrievePopularTags(int page, int limit);

}
