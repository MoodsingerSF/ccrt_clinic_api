package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;

public interface FeeService {
  FeeEntity getFeeOfDoctor(String doctorUserId);
}
