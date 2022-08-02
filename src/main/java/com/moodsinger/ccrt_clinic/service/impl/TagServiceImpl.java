package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

}
