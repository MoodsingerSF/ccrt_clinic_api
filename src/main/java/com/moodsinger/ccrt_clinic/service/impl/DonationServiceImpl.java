package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.DonationRequestServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.entity.DonationEntity;
import com.moodsinger.ccrt_clinic.io.entity.DonationRequestEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.repository.DonationRepository;
import com.moodsinger.ccrt_clinic.io.repository.DonationRequestRepository;
import com.moodsinger.ccrt_clinic.service.DonationRequestService;
import com.moodsinger.ccrt_clinic.service.DonationService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.DonationDto;

@Service
public class DonationServiceImpl implements DonationService {
  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private DonationRequestService donationRequestService;
  @Autowired
  private DonationRepository donationRepository;
  @Autowired
  private DonationRequestRepository donationRequestRepository;
  @Autowired
  private Utils utils;

  @Transactional
  @Override
  synchronized public DonationDto createDonation(DonationDto donationDto) {
    if (donationDto.getAmount() <= 0) {
      throw new DonationRequestServiceException(MessageCodes.INVALID_REQUEST.name(), Messages.AMOUNT_ERROR.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    UserEntity donor = userService.findUserEntity(donationDto.getDonorUserId());
    DonationRequestEntity donationRequestEntity = donationRequestService
        .findDonationRequest(donationDto.getDonationRequestId());
    DonationEntity donationEntity = modelMapper.map(donationDto, DonationEntity.class);
    donationEntity.setDonor(donor);
    donationEntity.setDonationRequest(donationRequestEntity);
    donationEntity.setDonationId(utils.generateDonationId());
    DonationEntity createdDonationEntity = donationRepository.save(donationEntity);
    double requestedAmount = donationRequestEntity.getAmount();
    double amountCollectedSoFar = donationRepository.getAmountDonatedSoFar(donationRequestEntity.getRequestId());

    if (amountCollectedSoFar >= requestedAmount) {
      donationRequestEntity.setCompletionStatus(CompletionStatus.COMPLETE);
      donationRequestRepository.save(donationRequestEntity);
    }
    return modelMapper.map(createdDonationEntity, DonationDto.class);
  }

  @Override
  public List<DonationDto> getDonations(int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("creationTime").descending());
    Page<DonationEntity> donationEntitiesPage = donationRepository.findAll(pageable);
    return processPage(donationEntitiesPage);
  }

  @Override
  public List<DonationDto> getUserDonations(String userId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("creationTime").descending());
    Page<DonationEntity> donationEntitiesPage = donationRepository.findAllByDonorUserId(userId, pageable);
    return processPage(donationEntitiesPage);
  }

  @Override
  public List<DonationDto> findDonationsMadeToADonationRequest(String requestId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("creationTime").descending());
    Page<DonationEntity> donationEntitiesPage = donationRepository.findAllByDonationRequestRequestId(requestId,
        pageable);
    return processPage(donationEntitiesPage);
  }

  private List<DonationDto> processPage(Page<DonationEntity> page) {
    List<DonationEntity> donationEntities = page.getContent();
    List<DonationDto> donationDtos = new ArrayList<>();
    for (DonationEntity donationEntity : donationEntities) {
      donationDtos.add(modelMapper.map(donationEntity, DonationDto.class));
    }
    return donationDtos;
  }

}
