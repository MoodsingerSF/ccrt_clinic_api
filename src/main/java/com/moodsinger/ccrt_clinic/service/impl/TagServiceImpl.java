package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;
import com.moodsinger.ccrt_clinic.io.entity.TagEntity;
import com.moodsinger.ccrt_clinic.io.repository.TagRepository;
import com.moodsinger.ccrt_clinic.service.TagService;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

@Service
public class TagServiceImpl implements TagService {

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
    System.out.println(tagDto);
    System.out.println("--------------" + tagDto.getName() + "---------------");
    TagEntity foundTagEntity = tagRepository.findByName(tagDto.getName());
    returnEntity = foundTagEntity;
    System.out.println("found tag entity= " + foundTagEntity);
    if (foundTagEntity == null) {
      System.out.println("*****************tag entity not found*****************8");
      foundTagEntity = modelMapper.map(tagDto, TagEntity.class);
      foundTagEntity.setBlogs(new ArrayList<BlogEntity>());
      System.out.println("*************" + foundTagEntity.getName() + "*************");
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

}
