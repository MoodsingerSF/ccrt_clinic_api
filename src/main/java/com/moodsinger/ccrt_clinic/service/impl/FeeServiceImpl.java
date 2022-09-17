package com.moodsinger.ccrt_clinic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;
import com.moodsinger.ccrt_clinic.io.repository.FeeRepository;
import com.moodsinger.ccrt_clinic.service.FeeService;

@Service
public class FeeServiceImpl implements FeeService {

  @Autowired
  private FeeRepository feeRepository;

  @Override
  public FeeEntity getFeeOfDoctor(String doctorUserId) {
    Pageable pageable = PageRequest.of(0, 1, Sort.by("id").descending());

    Page<FeeEntity> feePage = feeRepository.findByUserUserId(doctorUserId, pageable);
    List<FeeEntity> feeList = feePage.getContent();
    return feeList.get(0);
  }

}
