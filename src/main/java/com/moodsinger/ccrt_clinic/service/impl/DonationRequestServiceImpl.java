package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.DonationRequestServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.DonationRequestEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.io.repository.DonationRequestRepository;
import com.moodsinger.ccrt_clinic.service.DonationRequestService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.DonationRequestDto;

@Service
public class DonationRequestServiceImpl implements DonationRequestService {
  @Autowired
  private DonationRequestRepository donationRequestRepository;

  @Autowired
  private UserService userService;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private Utils utils;

  @Transactional
  @Override
  public DonationRequestDto createDonationRequest(DonationRequestDto donationRequestDto) {
    if (donationRequestDto.getAmount() <= 0) {
      throw new DonationRequestServiceException(MessageCodes.INVALID_REQUEST.name(), Messages.AMOUNT_ERROR.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    UserEntity requestor = userService.findUserEntity(donationRequestDto.getRequestorUserId());
    DonationRequestEntity donationRequestEntity = modelMapper.map(donationRequestDto, DonationRequestEntity.class);
    donationRequestEntity.setRequestor(requestor);
    donationRequestEntity.setRequestId(utils.generateDonationRequestId());
    donationRequestEntity.setCompletionStatus(CompletionStatus.INCOMPLETE);
    donationRequestEntity.setRequestStatus(VerificationStatus.PENDING);
    DonationRequestEntity createdDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(createdDonationRequestEntity, DonationRequestDto.class);
  }

  @Override
  public List<DonationRequestDto> retrieveDonationRequests(int page, int limit, CompletionStatus completionStatus,
      VerificationStatus requestStatus) {
    Page<DonationRequestEntity> donationRequestsPage = donationRequestRepository
        .findAllByRequestStatusAndCompletionStatus(
            requestStatus, completionStatus, PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    return processPage(donationRequestsPage);

  }

  @Transactional
  @Override
  public DonationRequestDto updateRequestStatus(String donationRequestId, VerificationStatus requestStatus) {
    DonationRequestEntity donationRequestEntity = findDonationRequest(donationRequestId);
    donationRequestEntity.setRequestStatus(requestStatus);
    DonationRequestEntity updatedDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(updatedDonationRequestEntity, DonationRequestDto.class);
  }

  @Transactional
  @Override
  public DonationRequestDto updateCompletionStatus(String donationRequestId, CompletionStatus completionStatus) {
    DonationRequestEntity donationRequestEntity = findDonationRequest(donationRequestId);
    donationRequestEntity.setCompletionStatus(completionStatus);
    DonationRequestEntity updatedDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(updatedDonationRequestEntity, DonationRequestDto.class);
  }

  @Override
  public DonationRequestEntity findDonationRequest(String requestId) {
    DonationRequestEntity donationRequestEntity = donationRequestRepository.findByRequestId(requestId);
    if (donationRequestEntity == null) {
      throw new DonationRequestServiceException(MessageCodes.DONATION_REQUEST_NOT_FOUND.name(),
          Messages.DONATION_REQUEST_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    return donationRequestEntity;
  }

  @Override
  public List<DonationRequestDto> getUserDonationRequests(String userId, int page, int limit) {
    Page<DonationRequestEntity> donationRequestsPage = donationRequestRepository
        .findAllByRequestorUserId(
            userId, PageRequest.of(page, limit, Sort.by("creationTime").descending()));
    return processPage(donationRequestsPage);
  }

  private List<DonationRequestDto> processPage(Page<DonationRequestEntity> page) {
    List<DonationRequestEntity> donationRequestEntities = page.getContent();
    List<DonationRequestDto> donationRequestDtos = new ArrayList<>();
    for (DonationRequestEntity donationRequestEntity : donationRequestEntities) {
      donationRequestDtos.add(modelMapper.map(donationRequestEntity, DonationRequestDto.class));
    }
    return donationRequestDtos;
  }

  @Override
  public DonationRequestDto updateDescription(String donationRequestId, String description) {
    DonationRequestEntity donationRequestEntity = findDonationRequest(donationRequestId);
    donationRequestEntity.setDescription(description);
    DonationRequestEntity updatedDonationRequestEntity = donationRequestRepository.save(donationRequestEntity);
    return modelMapper.map(updatedDonationRequestEntity, DonationRequestDto.class);
  }

}
