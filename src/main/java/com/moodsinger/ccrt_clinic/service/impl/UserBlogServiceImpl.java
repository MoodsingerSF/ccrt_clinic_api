package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;
import com.moodsinger.ccrt_clinic.io.repository.BlogRepository;
import com.moodsinger.ccrt_clinic.service.UserBlogService;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;

@Service
public class UserBlogServiceImpl implements UserBlogService {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<BlogDto> getUserBlogs(String userId, int page, int limit) {
    List<BlogDto> foundBlogs = new ArrayList<>();
    Pageable pageable = PageRequest.of(page, limit);
    Page<BlogEntity> foundBlogPage = blogRepository.findAllByCreatorUserId(userId, pageable);
    List<BlogEntity> foundBlogEntities = foundBlogPage.getContent();
    for (BlogEntity blogEntity : foundBlogEntities) {
      foundBlogs.add(modelMapper.map(blogEntity, BlogDto.class));
    }
    return foundBlogs;
  }

}
