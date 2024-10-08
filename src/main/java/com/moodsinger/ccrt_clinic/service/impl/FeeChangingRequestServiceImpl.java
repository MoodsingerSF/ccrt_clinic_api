package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.FeeChangingRequestServiceException;
import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.FeeChangingRequestEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.SortType;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.io.repository.FeeChangingRequestRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.FeeChangingRequestService;
import com.moodsinger.ccrt_clinic.service.FeeService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.FeeChangingRequestDto;
import com.moodsinger.ccrt_clinic.shared.dto.FeeDto;

@Service
public class FeeChangingRequestServiceImpl implements FeeChangingRequestService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private FeeChangingRequestRepository feeChangingRequestRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FeeService feeService;

  @Transactional
  @Override
  public FeeChangingRequestDto createFeeChangingRequest(FeeChangingRequestDto feeChangingRequestDto) {

    UserEntity doctorEntity = userRepository.findByUserId(feeChangingRequestDto.getUserId());
    if (doctorEntity == null) {
      throw new UserServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    Set<RoleEntity> roles = doctorEntity.getRoles();
    List<RoleEntity> roleEntities = new ArrayList<>(roles);
    Role role = roleEntities.get(0).getName();
    if (role != Role.DOCTOR) {
      throw new UserServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    Page<FeeChangingRequestEntity> feeChangingRequestEntitiesPage = feeChangingRequestRepository
        .findAllByUserUserIdAndStatus(feeChangingRequestDto.getUserId(), VerificationStatus.PENDING,
            PageRequest.of(0, 10));
    List<FeeChangingRequestEntity> feeChangingRequestEntitiesList = feeChangingRequestEntitiesPage.getContent();
    if (feeChangingRequestEntitiesList != null && feeChangingRequestEntitiesList.size() > 0) {
      throw new FeeChangingRequestServiceException(MessageCodes.FEE_CHANGING_REQUEST_CREATION_ERROR.name(),
          Messages.FEE_CHANGING_REQUEST_CREATION_ERROR.getMessage(), HttpStatus.FORBIDDEN);
    }

    FeeChangingRequestEntity feeChangingRequestEntity = modelMapper.map(feeChangingRequestDto,
        FeeChangingRequestEntity.class);
    feeChangingRequestEntity.setRequestId(utils.generateRequestId());
    feeChangingRequestEntity.setStatus(VerificationStatus.PENDING);
    feeChangingRequestEntity.setUser(doctorEntity);
    FeeChangingRequestEntity createdFeeChangingRequestEntity = feeChangingRequestRepository
        .save(feeChangingRequestEntity);
    return modelMapper.map(createdFeeChangingRequestEntity, FeeChangingRequestDto.class);
  }

  @Override
  public List<FeeChangingRequestDto> retrievePendingRequestsOfUser(String userId) {
    Page<FeeChangingRequestEntity> currentPage = feeChangingRequestRepository.findAllByUserUserIdAndStatus(userId,
        VerificationStatus.PENDING, PageRequest.of(0, 10, Sort.by("id").descending()));
    List<FeeChangingRequestEntity> feeChangingRequestEntities = currentPage.getContent();
    List<FeeChangingRequestDto> feeChangingRequestDtos = new ArrayList<>();
    for (FeeChangingRequestEntity feeChangingRequestEntity : feeChangingRequestEntities) {
      feeChangingRequestDtos.add(modelMapper.map(feeChangingRequestEntity, FeeChangingRequestDto.class));
    }
    return feeChangingRequestDtos;
  }

  @Override
  public List<FeeChangingRequestEntity> retrievePendingRequestEntitiesOfUser(String userId) {
    Page<FeeChangingRequestEntity> currentPage = feeChangingRequestRepository.findAllByUserUserIdAndStatus(userId,
        VerificationStatus.PENDING, PageRequest.of(0, 10, Sort.by("id").descending()));
    List<FeeChangingRequestEntity> feeChangingRequestEntities = currentPage.getContent();
    return feeChangingRequestEntities;
  }

  @Override
  public List<FeeChangingRequestDto> retrieveFeeChangingRequests(int page, int limit, VerificationStatus status,
      SortType sortType) {
    Sort sort = Sort.by("id").descending();
    if (sortType == SortType.ASC) {
      sort = Sort.by("id").ascending();
    }
    Pageable pageable = PageRequest.of(page, limit, sort);
    Page<FeeChangingRequestEntity> feeChangingRequestEntitiesPage = feeChangingRequestRepository.findByStatus(status,
        pageable);
    List<FeeChangingRequestEntity> feeChangingRequestEntities = feeChangingRequestEntitiesPage.getContent();
    List<FeeChangingRequestDto> feeChangingRequestDtos = new ArrayList<>();
    for (FeeChangingRequestEntity feeChangingRequestEntity : feeChangingRequestEntities) {
      feeChangingRequestDtos.add(modelMapper.map(feeChangingRequestEntity, FeeChangingRequestDto.class));
    }
    return feeChangingRequestDtos;
  }

  @Override
  public List<FeeChangingRequestDto> retrieveAllFeeChangingRequests(int page, int limit, SortType sortType) {
    Sort sort = Sort.by("id").descending();
    if (sortType == SortType.ASC) {
      sort = Sort.by("id").ascending();
    }
    Pageable pageable = PageRequest.of(page, limit, sort);
    Page<FeeChangingRequestEntity> feeChangingRequestEntitiesPage = feeChangingRequestRepository.findAll(pageable);
    List<FeeChangingRequestEntity> feeChangingRequestEntities = feeChangingRequestEntitiesPage.getContent();
    List<FeeChangingRequestDto> feeChangingRequestDtos = new ArrayList<>();
    for (FeeChangingRequestEntity feeChangingRequestEntity : feeChangingRequestEntities) {
      feeChangingRequestDtos.add(modelMapper.map(feeChangingRequestEntity, FeeChangingRequestDto.class));
    }
    return feeChangingRequestDtos;
  }

  @Transactional
  @Override
  public FeeChangingRequestDto updateFeeChangingRequest(String requestId, FeeChangingRequestDto feeChangingRequestDto) {
    FeeChangingRequestEntity feeChangingRequestEntity = feeChangingRequestRepository.findByRequestId(requestId);
    if (feeChangingRequestEntity == null) {
      throw new FeeChangingRequestServiceException(MessageCodes.REQUEST_NOT_FOUND.name(),
          Messages.REQUEST_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    UserEntity user = userRepository.findByUserId(feeChangingRequestDto.getAdminUserId());
    if (user == null) {
      throw new UserServiceException(MessageCodes.USER_NOT_FOUND.name(),
          Messages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    Set<RoleEntity> roles = user.getRoles();
    List<RoleEntity> roleEntities = new ArrayList<>(roles);
    Role role = roleEntities.get(0).getName();
    if (role != Role.ADMIN) {
      throw new UserServiceException(MessageCodes.FORBIDDEN.name(),
          Messages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    VerificationStatus status = feeChangingRequestDto.getStatus();
    feeChangingRequestEntity.setStatus(status);
    feeChangingRequestEntity.setResolver(user);
    FeeChangingRequestEntity updatedFeeChangingRequestEntity = feeChangingRequestRepository
        .save(feeChangingRequestEntity);
    if (status == VerificationStatus.ACCEPTED) {
      FeeDto feeDto = new FeeDto(feeChangingRequestEntity.getAmount(), feeChangingRequestEntity.getUser().getUserId());
      feeService.addFee(feeDto);
    }
    return modelMapper.map(updatedFeeChangingRequestEntity, FeeChangingRequestDto.class);
  }

  @Override
  public List<FeeChangingRequestEntity> retrieveAllRequestEntitiesOfUser(String userId) {
    List<FeeChangingRequestEntity> feeChangingRequestEntities = feeChangingRequestRepository
        .findAllByUserUserId(userId);
    return feeChangingRequestEntities;
  }

}
