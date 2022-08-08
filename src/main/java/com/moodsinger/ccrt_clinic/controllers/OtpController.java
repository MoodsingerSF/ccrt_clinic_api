package com.moodsinger.ccrt_clinic.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.OtpCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.OtpValidationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.OtpRest;
import com.moodsinger.ccrt_clinic.service.OtpService;
import com.moodsinger.ccrt_clinic.shared.dto.OtpDto;

@RestController
@RequestMapping("otp")
public class OtpController {

  @Autowired
  private OtpService otpService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public OtpRest sendOtp(@RequestBody OtpCreationRequestModel otpCreationRequestModel) {
    OtpDto otpDto = modelMapper.map(otpCreationRequestModel, OtpDto.class);
    OtpDto createdOtp = otpService.sendOtp(otpDto);
    OtpRest otpRest = modelMapper.map(createdOtp, OtpRest.class);
    return otpRest;
  }

  @PostMapping("/validation")
  public OtpRest validateOtp(@RequestBody OtpValidationRequestModel otpValidationRequestModel) {
    OtpDto otpDto = modelMapper.map(otpValidationRequestModel, OtpDto.class);
    otpService.validateOtp(otpDto);
    return null;
  }
}
