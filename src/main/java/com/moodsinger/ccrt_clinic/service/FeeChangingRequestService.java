package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.entity.FeeChangingRequestEntity;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.shared.dto.FeeChangingRequestDto;

public interface FeeChangingRequestService {
  FeeChangingRequestDto createFeeChangingRequest(FeeChangingRequestDto feeChangingRequestDto);

  List<FeeChangingRequestDto> retrievePendingRequestsOfUser(String userId);

  List<FeeChangingRequestDto> retrieveFeeChangingRequests(int page, int limit, VerificationStatus status);

  List<FeeChangingRequestDto> retrieveAllFeeChangingRequests(int page, int limit);

  List<FeeChangingRequestEntity> retrievePendingRequestEntitiesOfUser(String userId);

  FeeChangingRequestDto updateFeeChangingRequest(String requestId, FeeChangingRequestDto feeChangingRequestDto);

}
