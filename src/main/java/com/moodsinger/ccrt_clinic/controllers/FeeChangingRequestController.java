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

import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.FeeChangingRequestModel;
import com.moodsinger.ccrt_clinic.model.request.FeeChangingRequestUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.FeeChangingRequestRest;
import com.moodsinger.ccrt_clinic.service.FeeChangingRequestService;
import com.moodsinger.ccrt_clinic.shared.dto.FeeChangingRequestDto;

@RestController
@RequestMapping("fee-changing-requests")
public class FeeChangingRequestController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private FeeChangingRequestService feeChangingRequestService;

  @PostMapping
  public FeeChangingRequestRest createFeeChangingRequest(
      @RequestBody FeeChangingRequestModel feeChangingRequestModel) {
    FeeChangingRequestDto feeChangingRequestDto = modelMapper.map(feeChangingRequestModel, FeeChangingRequestDto.class);
    FeeChangingRequestDto createdFeeChangingRequestDto = feeChangingRequestService
        .createFeeChangingRequest(feeChangingRequestDto);
    return modelMapper.map(createdFeeChangingRequestDto, FeeChangingRequestRest.class);
  }

  @GetMapping
  public List<FeeChangingRequestRest> getFeeChangingRequests(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "status", required = false) VerificationStatus status) {
    List<FeeChangingRequestDto> feeChangingRequestDtos;
    if (status == VerificationStatus.ACCEPTED || status == VerificationStatus.PENDING
        || status == VerificationStatus.REJECTED) {
      feeChangingRequestDtos = feeChangingRequestService.retrieveFeeChangingRequests(page, limit, status);
    } else {
      feeChangingRequestDtos = feeChangingRequestService.retrieveAllFeeChangingRequests(page, limit);
    }
    List<FeeChangingRequestRest> feeChangingRequestRests = new ArrayList<>();
    for (FeeChangingRequestDto feeChangingRequestDto : feeChangingRequestDtos) {
      feeChangingRequestRests.add(modelMapper.map(feeChangingRequestDto, FeeChangingRequestRest.class));
    }
    return feeChangingRequestRests;
  }

  @PutMapping(path = "/{requestId}")
  private FeeChangingRequestRest updateRequestStatus(@PathVariable String requestId,
      @RequestBody FeeChangingRequestUpdateRequestModel feeChangingRequestUpdateRequestModel) {
    FeeChangingRequestDto feeChangingRequestDto = modelMapper.map(feeChangingRequestUpdateRequestModel,
        FeeChangingRequestDto.class);
    FeeChangingRequestDto createdFeeChangingRequestDto = feeChangingRequestService
        .updateFeeChangingRequest(requestId, feeChangingRequestDto);
    return modelMapper.map(createdFeeChangingRequestDto, FeeChangingRequestRest.class);
  }
}
