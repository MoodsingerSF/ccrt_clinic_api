package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.shared.dto.OtpDto;

public interface OtpService {
  OtpDto sendOtp(OtpDto otpDto);

  void validateOtp(OtpDto otpDto);
}
