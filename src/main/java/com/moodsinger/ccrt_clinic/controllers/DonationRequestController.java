package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.DonationRequestCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.DonationUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.DonationRequestRest;
import com.moodsinger.ccrt_clinic.model.response.DonationRest;
import com.moodsinger.ccrt_clinic.service.DonationRequestService;
import com.moodsinger.ccrt_clinic.service.DonationService;
import com.moodsinger.ccrt_clinic.shared.dto.DonationDto;
import com.moodsinger.ccrt_clinic.shared.dto.DonationRequestDto;

@RestController
@RequestMapping("donation-requests")
public class DonationRequestController {
  @Autowired
  private DonationRequestService donationRequestService;

  @Autowired
  private DonationService donationService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public DonationRequestRest createDonationRequest(
      @RequestBody DonationRequestCreationRequestModel donationRequestCreationRequestModel) {

    DonationRequestDto createdDonationRequestDto = donationRequestService
        .createDonationRequest(modelMapper.map(donationRequestCreationRequestModel, DonationRequestDto.class));
    return modelMapper.map(createdDonationRequestDto, DonationRequestRest.class);
  }

  @GetMapping
  public List<DonationRequestRest> getDonationRequests(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "request-status", required = false, defaultValue = "ACCEPTED") VerificationStatus requestStatus,
      @RequestParam(name = "completion-status", required = false, defaultValue = "COMPLETE") CompletionStatus completionStatus) {
    List<DonationRequestDto> donationRequests = donationRequestService.retrieveDonationRequests(page, limit,
        completionStatus, requestStatus);
    return processData(donationRequests);
  }

  @PutMapping("/{requestId}/description")
  public DonationRequestRest updateDescription(@PathVariable String requestId,
      @RequestBody DonationUpdateRequestModel donationUpdateRequestModel) {
    DonationRequestDto createdDonationRequestDto = donationRequestService
        .updateDescription(requestId, donationUpdateRequestModel.getDescription());
    return modelMapper.map(createdDonationRequestDto, DonationRequestRest.class);
  }

  @PutMapping("/{requestId}/request-status")
  public DonationRequestRest updateRequestStatus(@PathVariable String requestId,
      @RequestBody DonationUpdateRequestModel donationUpdateRequestModel) {
    DonationRequestDto createdDonationRequestDto = donationRequestService
        .updateRequestStatus(requestId, donationUpdateRequestModel.getRequestStatus());
    return modelMapper.map(createdDonationRequestDto, DonationRequestRest.class);
  }

  @PutMapping("/{requestId}/completion-status")
  public DonationRequestRest updateCompletionStatus(@PathVariable String requestId,
      @RequestBody DonationUpdateRequestModel donationUpdateRequestModel) {
    DonationRequestDto createdDonationRequestDto = donationRequestService
        .updateCompletionStatus(requestId, donationUpdateRequestModel.getCompletionStatus());
    return modelMapper.map(createdDonationRequestDto, DonationRequestRest.class);
  }

  @GetMapping("/{requestId}/donations")
  public List<DonationRest> retrieveRequestDonations(@PathVariable String requestId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<DonationDto> donationRequests = donationService.findDonationsMadeToADonationRequest(requestId,
        page, limit);
    List<DonationRest> donationRests = new ArrayList<>();
    for (DonationDto donationDto : donationRequests) {
      donationRests.add(modelMapper.map(donationDto, DonationRest.class));
    }
    return donationRests;

  }

  private List<DonationRequestRest> processData(List<DonationRequestDto> donationRequests) {
    List<DonationRequestRest> donationRequestRests = new ArrayList<>();
    for (DonationRequestDto donationRequestDto : donationRequests) {
      donationRequestRests.add(modelMapper.map(donationRequestDto, DonationRequestRest.class));
    }
    return donationRequestRests;
  }
}
