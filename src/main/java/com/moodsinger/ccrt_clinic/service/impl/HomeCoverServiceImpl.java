package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.HomeCoverException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.HomeCoverEntity;
import com.moodsinger.ccrt_clinic.io.enums.HomeCoverType;
import com.moodsinger.ccrt_clinic.io.enums.VisibilityType;
import com.moodsinger.ccrt_clinic.io.repository.HomeCoverRepository;
import com.moodsinger.ccrt_clinic.service.BlogService;
import com.moodsinger.ccrt_clinic.service.HomeCoverService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.HomeCoverDto;

@Service
public class HomeCoverServiceImpl implements HomeCoverService {

  @Autowired
  private HomeCoverRepository homeCoverRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Autowired
  private BlogService blogService;

  @Autowired
  private UserService userService;

  @Transactional
  @Override
  public HomeCoverDto addNewHomeCover(HomeCoverDto homeCoverDto) {
    HomeCoverEntity homeCoverEntity = modelMapper.map(homeCoverDto, HomeCoverEntity.class);
    homeCoverEntity.setCoverId(utils.generateHomeCoverCoverId());

    if (homeCoverDto.getType() == HomeCoverType.BLOG) {
      homeCoverEntity.setImageUrl(blogService.getBlog(homeCoverDto.getItemId()).getImageUrl());
    } else if (homeCoverDto.getType() == HomeCoverType.DOCTOR) {
      homeCoverEntity.setImageUrl(userService.getUserByUserId(homeCoverDto.getItemId()).getProfileImageUrl());
    }

    HomeCoverEntity createdHomeCoverEntity = homeCoverRepository.save(homeCoverEntity);
    return modelMapper.map(createdHomeCoverEntity, HomeCoverDto.class);
  }

  @Override
  public List<HomeCoverDto> retrieveHomeCovers(VisibilityType visibilityType) {
    List<HomeCoverEntity> foundHomeCoverEntities = homeCoverRepository.findAllByIsVisible(
        visibilityType == VisibilityType.VISIBLE,
        Sort.by("creationTime").descending());
    List<HomeCoverDto> homeCoverDtos = new ArrayList<>();
    for (HomeCoverEntity homeCoverEntity : foundHomeCoverEntities) {
      homeCoverDtos.add(modelMapper.map(homeCoverEntity, HomeCoverDto.class));
    }
    return homeCoverDtos;
  }

  @Override
  public void deleteCover(String coverId) {
    HomeCoverEntity homeCoverEntity = homeCoverRepository.findByCoverId(coverId);
    if (homeCoverEntity == null)
      throw new HomeCoverException(MessageCodes.HOME_COVER_NOT_FOUND.name(), Messages.HOME_COVER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    homeCoverRepository.delete(homeCoverEntity);
  }

  @Transactional
  @Override
  public HomeCoverDto updateHomeCover(String coverId, HomeCoverDto homeCoverDto) {
    HomeCoverEntity homeCoverEntity = homeCoverRepository.findByCoverId(coverId);
    if (homeCoverEntity == null)
      throw new HomeCoverException(MessageCodes.HOME_COVER_NOT_FOUND.name(), Messages.HOME_COVER_NOT_FOUND.getMessage(),
          HttpStatus.NOT_FOUND);
    homeCoverEntity.setVisible(homeCoverDto.getVisibilityType() == VisibilityType.VISIBLE);
    HomeCoverEntity updatedHomeCoverEntity = homeCoverRepository.save(homeCoverEntity);
    return modelMapper.map(updatedHomeCoverEntity, HomeCoverDto.class);
  }

}
