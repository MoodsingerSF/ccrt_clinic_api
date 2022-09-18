package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.BlogCountByTag;
import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;
import com.moodsinger.ccrt_clinic.io.entity.TagEntity;
import com.moodsinger.ccrt_clinic.io.repository.TagRepository;
import com.moodsinger.ccrt_clinic.model.response.BlogCountByTagRest;
import com.moodsinger.ccrt_clinic.service.TagService;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

@Service
public class TagServiceImpl implements TagService {

  final long NUMBER_OF_BLOGS_TO_CONSIDER_TO_FIND_POPULAR_TAGS = 100;
  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public TagDto createTag(TagDto tagDto) {
    ModelMapper modelMapper = new ModelMapper();
    TagEntity tagEntity = modelMapper.map(tagDto, TagEntity.class);
    TagEntity createdTagEntity = tagRepository.save(tagEntity);
    TagDto createdTagDto = modelMapper.map(createdTagEntity, TagDto.class);
    return createdTagDto;
  }

  @Override
  public TagEntity getOrCreateTag(TagDto tagDto) {
    ModelMapper modelMapper = new ModelMapper();
    TagEntity returnEntity;
    TagEntity foundTagEntity = tagRepository.findByName(tagDto.getName());
    returnEntity = foundTagEntity;
    if (foundTagEntity == null) {
      foundTagEntity = modelMapper.map(tagDto, TagEntity.class);
      foundTagEntity.setBlogs(new ArrayList<BlogEntity>());
      returnEntity = tagRepository.save(foundTagEntity);
    }
    return returnEntity;
  }

  @Override
  public List<TagDto> searchTagsByPrefix(String prefix, int page, int limit) {
    Page<TagEntity> foundTagEntities = tagRepository.findByNameStartsWithIgnoreCase(prefix,
        PageRequest.of(page, limit, Sort.by("name").ascending()));
    List<TagEntity> foundTagEntitiesList = foundTagEntities.getContent();
    List<TagDto> foundTagDtos = new ArrayList<>();
    for (TagEntity tagEntity : foundTagEntitiesList) {
      foundTagDtos.add(modelMapper.map(tagEntity, TagDto.class));
    }
    return foundTagDtos;
  }

  @Override
  public List<BlogCountByTagRest> retrievePopularTags(int page, int limit) {
    long maxId = tagRepository.getMaxId();
    Page<BlogCountByTag> foundTagEntities = tagRepository.findPopularTags(
        maxId - NUMBER_OF_BLOGS_TO_CONSIDER_TO_FIND_POPULAR_TAGS,
        PageRequest.of(page, limit));
    List<BlogCountByTag> foundTagEntitiesList = foundTagEntities.getContent();
    List<BlogCountByTagRest> returnEntities = new ArrayList<>();
    for (BlogCountByTag blogCountByTag : foundTagEntitiesList) {
      returnEntities.add(modelMapper.map(blogCountByTag, BlogCountByTagRest.class));
    }
    return returnEntities;
  }

}
