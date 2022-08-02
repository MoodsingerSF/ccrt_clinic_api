package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.io.entity.TagEntity;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

public interface TagService {
  TagDto createTag(TagDto tagDto);

  TagEntity getOrCreateTag(TagDto tagDto);

}
