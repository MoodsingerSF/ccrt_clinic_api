package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.io.entity.DonationRequestEntity;
import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.shared.dto.DonationRequestDto;

public interface DonationRequestService {
  DonationRequestDto createDonationRequest(DonationRequestDto donationRequestDto);

  DonationRequestDto updateRequestStatus(String donationRequestId, VerificationStatus requestStatus);

  DonationRequestDto updateCompletionStatus(String donationRequestId, CompletionStatus completionStatus);

  List<DonationRequestDto> retrieveDonationRequests(int page, int limit,
      CompletionStatus completionStatus, VerificationStatus requestStatus);

  DonationRequestEntity findDonationRequest(String requestId);

  List<DonationRequestDto> getUserDonationRequests(String userId, int page, int limit);

}
