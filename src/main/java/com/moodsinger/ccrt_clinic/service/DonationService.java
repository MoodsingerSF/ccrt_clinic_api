package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.shared.dto.DonationDto;

public interface DonationService {
  DonationDto createDonation(DonationDto donationDto);

  List<DonationDto> getDonations(int page, int limit);

  List<DonationDto> getUserDonations(String userId, int page, int limit);

  List<DonationDto> findDonationsMadeToADonationRequest(String requestId, int page, int limit);

}
