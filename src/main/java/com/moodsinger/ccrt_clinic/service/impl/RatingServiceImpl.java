package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.RatingServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.AverageRating;
import com.moodsinger.ccrt_clinic.io.entity.RatingCriteriaEntity;
import com.moodsinger.ccrt_clinic.io.entity.RatingEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.repository.RatingCriteriaRepository;
import com.moodsinger.ccrt_clinic.io.repository.RatingRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.model.request.RatingCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.RatingInSingleCriteriaCreationRequestModel;
import com.moodsinger.ccrt_clinic.service.RatingService;
import com.moodsinger.ccrt_clinic.shared.dto.RatingDto;

@Service
public class RatingServiceImpl implements RatingService {

  @Autowired
  private RatingRepository ratingRepository;

  @Autowired
  private RatingCriteriaRepository ratingCriteriaRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  @Override
  public List<RatingDto> addRatings(String doctorUserId, RatingCreationRequestModel ratingCreationRequestModel) {
    UserEntity doctorEntity = userRepository.findByUserId(doctorUserId);

    if (doctorEntity == null) {
      throw new RatingServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RoleEntity> roles = new ArrayList<>(doctorEntity.getRoles());
    Role role = roles.get(0).getName();
    if (role != Role.DOCTOR) {
      throw new RatingServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    UserEntity userEntity = userRepository.findByUserId(ratingCreationRequestModel.getRatingGiverUserId());
    if (userEntity == null) {
      throw new RatingServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RatingEntity> ratingEntities = new ArrayList<>();
    for (RatingInSingleCriteriaCreationRequestModel ratingInSingleCriteriaCreationRequestModel : ratingCreationRequestModel
        .getRatings()) {
      System.out.println(ratingInSingleCriteriaCreationRequestModel);
      RatingEntity ratingEntity = new RatingEntity();
      ratingEntity.setDoctor(doctorEntity);
      ratingEntity.setPatient(userEntity);
      RatingCriteriaEntity ratingCriteriaEntity = ratingCriteriaRepository
          .findById(ratingInSingleCriteriaCreationRequestModel.getCriteriaId());
      double rating = ratingInSingleCriteriaCreationRequestModel.getRating();
      if (rating < 0 || rating > ratingCriteriaEntity.getMaxValue()) {
        throw new RatingServiceException(MessageCodes.VALUE_OUT_OF_RANGE.name(),
            "Rating value must be in the range between 0 to " + ratingCriteriaEntity.getMaxValue(),
            HttpStatus.BAD_REQUEST);
      }
      ratingEntity.setRating(rating);
      ratingEntity
          .setRatingCriteria(ratingCriteriaEntity);

      ratingEntities.add(ratingEntity);

    }
    List<RatingEntity> createdRatingEntities = (List<RatingEntity>) ratingRepository.saveAll(ratingEntities);

    List<RatingDto> ratingDtos = new ArrayList<>();
    for (RatingEntity ratingEntity : createdRatingEntities) {
      ratingDtos.add(modelMapper.map(ratingEntity, RatingDto.class));
    }
    return ratingDtos;
  }

  @Override
  public List<RatingDto> retrieveRatings(String doctorUserId, String raterUserId) {
    List<RatingEntity> ratingEntities = ratingRepository.findAllByPatientUserIdAndDoctorUserId(raterUserId,
        doctorUserId);
    List<RatingDto> ratingDtos = new ArrayList<>();
    for (RatingEntity ratingEntity : ratingEntities) {
      ratingDtos.add(modelMapper.map(ratingEntity, RatingDto.class));
    }
    return ratingDtos;
  }

  @Override
  public List<RatingDto> retrieveAverageRatings(String doctorUserId) {
    List<AverageRating> ratingEntities = ratingRepository.getAverageRatingOfDoctor(doctorUserId);
    List<RatingDto> ratingDtos = new ArrayList<>();
    for (AverageRating averageRating : ratingEntities) {
      ratingDtos.add(modelMapper.map(averageRating, RatingDto.class));
    }
    return ratingDtos;
  }

  @Transactional
  @Override
  public List<RatingDto> updateRatings(String doctorUserId, RatingCreationRequestModel ratingCreationRequestModel) {
    UserEntity doctorEntity = userRepository.findByUserId(doctorUserId);

    if (doctorEntity == null) {
      throw new RatingServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RoleEntity> roles = new ArrayList<>(doctorEntity.getRoles());
    Role role = roles.get(0).getName();
    if (role != Role.DOCTOR) {
      throw new RatingServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    UserEntity userEntity = userRepository.findByUserId(ratingCreationRequestModel.getRatingGiverUserId());
    if (userEntity == null) {
      throw new RatingServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    List<RatingEntity> ratingEntities = new ArrayList<>();
    for (RatingInSingleCriteriaCreationRequestModel ratingInSingleCriteriaCreationRequestModel : ratingCreationRequestModel
        .getRatings()) {
      RatingEntity ratingEntity = ratingRepository.findByRatingCriteriaIdAndDoctorUserIdAndPatientUserId(
          ratingInSingleCriteriaCreationRequestModel.getCriteriaId(), doctorUserId, userEntity.getUserId());
      double rating = ratingInSingleCriteriaCreationRequestModel.getRating();
      if (rating < 0 || rating > ratingEntity.getRatingCriteria().getMaxValue()) {
        throw new RatingServiceException(MessageCodes.VALUE_OUT_OF_RANGE.name(),
            "Rating value must be in the range between 0 to " + ratingEntity.getRatingCriteria().getMaxValue(),
            HttpStatus.BAD_REQUEST);
      }
      ratingEntity.setRating(rating);

      ratingEntities.add(ratingEntity);

    }
    List<RatingEntity> updatedRatingEntities = (List<RatingEntity>) ratingRepository.saveAll(ratingEntities);

    List<RatingDto> ratingDtos = new ArrayList<>();
    for (RatingEntity ratingEntity : updatedRatingEntities) {
      ratingDtos.add(modelMapper.map(ratingEntity, RatingDto.class));
    }
    return ratingDtos;
  }

}
