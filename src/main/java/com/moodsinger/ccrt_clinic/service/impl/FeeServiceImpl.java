package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.repository.FeeRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.FeeService;
import com.moodsinger.ccrt_clinic.shared.dto.FeeDto;

@Service
public class FeeServiceImpl implements FeeService {

  @Autowired
  private FeeRepository feeRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public FeeEntity getFeeOfDoctor(String doctorUserId) {
    Pageable pageable = PageRequest.of(0, 1, Sort.by("id").descending());

    Page<FeeEntity> feePage = feeRepository.findByUserUserId(doctorUserId, pageable);
    List<FeeEntity> feeList = feePage.getContent();
    return feeList.get(0);
  }

  @Override
  public FeeDto addFee(FeeDto feeDto) {
    UserEntity doctorEntity = userRepository.findByUserId(feeDto.getUserId());
    if (doctorEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
    }
    Set<RoleEntity> roles = doctorEntity.getRoles();
    List<RoleEntity> roleEntities = new ArrayList<>(roles);
    Role role = roleEntities.get(0).getName();
    if (role != Role.DOCTOR) {
      throw new UserServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    FeeEntity feeEntity = modelMapper.map(feeDto, FeeEntity.class);
    feeEntity.setUser(doctorEntity);
    FeeEntity createdFeeEntity = feeRepository.save(feeEntity);
    return modelMapper.map(createdFeeEntity, FeeDto.class);
  }

}
