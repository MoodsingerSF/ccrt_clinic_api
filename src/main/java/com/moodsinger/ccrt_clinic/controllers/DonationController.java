package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.DonationCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.DonationRest;
import com.moodsinger.ccrt_clinic.service.DonationService;
import com.moodsinger.ccrt_clinic.shared.dto.DonationDto;

@RestController
@RequestMapping("donations")
public class DonationController {

  @Autowired
  private DonationService donationService;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  @PostMapping
  public DonationRest createDonation(@RequestBody DonationCreationRequestModel donationCreationRequestModel) {
    if (donationCreationRequestModel.getDonorUserId() == null
        || donationCreationRequestModel.getDonorUserId().isEmpty()) {
      donationCreationRequestModel.setDonorUserId("0OLV40HGpQ1oznuAGxb1EshWIElYsw");
    }
    if (donationCreationRequestModel.getDonationRequestId() == null
        || donationCreationRequestModel.getDonationRequestId().isEmpty()) {
      donationCreationRequestModel.setDonationRequestId("kd26s25SWEC5oIeVotCh");
    }
    DonationDto donationDto = donationService
        .createDonation(modelMapper.map(donationCreationRequestModel, DonationDto.class));
    return modelMapper.map(donationDto, DonationRest.class);
  }

  @GetMapping
  public List<DonationRest> retrieveDonations(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<DonationDto> donationDtos = donationService
        .getDonations(page, limit);
    List<DonationRest> donationRests = new ArrayList<>();
    for (DonationDto donationDto : donationDtos) {
      donationRests.add(modelMapper.map(donationDto, DonationRest.class));
    }
    return donationRests;
  }
}
