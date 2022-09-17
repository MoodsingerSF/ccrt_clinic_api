package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;
import com.moodsinger.ccrt_clinic.shared.dto.FeeDto;

public interface FeeService {
  FeeEntity getFeeOfDoctor(String doctorUserId);

  FeeDto addFee(FeeDto feeDto);
}
