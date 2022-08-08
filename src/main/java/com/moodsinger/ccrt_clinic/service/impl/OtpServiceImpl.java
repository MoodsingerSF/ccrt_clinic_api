package com.moodsinger.ccrt_clinic.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.AppProperties;
import com.moodsinger.ccrt_clinic.exceptions.OtpServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.OtpBlacklistEntity;
import com.moodsinger.ccrt_clinic.io.entity.OtpEntity;
import com.moodsinger.ccrt_clinic.io.repository.OtpBlacklistRepository;
import com.moodsinger.ccrt_clinic.io.repository.OtpRepository;
import com.moodsinger.ccrt_clinic.service.OtpService;
import com.moodsinger.ccrt_clinic.shared.AmazonSES;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.OtpDto;

@Service
public class OtpServiceImpl implements OtpService {

  @Autowired
  private OtpRepository otpRepository;

  @Autowired
  private OtpBlacklistRepository otpBlacklistRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private AmazonSES amazonSES;

  @Override
  public OtpDto sendOtp(OtpDto otpDto) {
    List<OtpBlacklistEntity> foundEntries = otpBlacklistRepository.findAllByEmail(otpDto.getEmail());
    boolean isBlocked = false;
    for (OtpBlacklistEntity otpBlacklistEntity : foundEntries) {
      if (utils.findDifference(otpBlacklistEntity.getTimeOfBlocking(), new Date()) <= Long
          .parseLong(appProperties.getProperty("blockDuration"))) {
        isBlocked = true;
      }
    }
    if (isBlocked == false) {
      List<OtpEntity> allOtps = otpRepository.findAllByEmail(otpDto.getEmail());
      long otpTries = 0;
      for (OtpEntity otpEntity : allOtps) {
        if (utils.findDifference(otpEntity.getCreationTime(), new Date()) <= Long
            .parseLong(appProperties.getProperty("blockDuration"))) {
          otpTries++;
        }
      }
      long maxConsecutiveOtpCodes = Long.parseLong(appProperties.getProperty("max-consecutive-otp-tries"));
      if ((otpTries + 1) == maxConsecutiveOtpCodes) {
        OtpBlacklistEntity otpBlacklistEntity2 = modelMapper.map(otpDto, OtpBlacklistEntity.class);
        otpBlacklistEntity2.setOtpBlacklistId(utils.generateOtpId(15));
        otpBlacklistRepository.save(otpBlacklistEntity2);
      }
      String otpCode = utils.generateOtpCode(6);
      amazonSES.sendVerificationEmail(otpDto.getEmail(), otpCode);
      OtpEntity otpEntity = modelMapper.map(otpDto, OtpEntity.class);
      otpEntity.setCode(otpCode);
      otpEntity.setOtpId(utils.generateOtpId(15));
      OtpEntity createdOtpEntity = otpRepository.save(otpEntity);
      OtpDto returnOtpDto = modelMapper.map(createdOtpEntity, OtpDto.class);
      return returnOtpDto;
    } else {
      throw new OtpServiceException(ExceptionErrorCodes.USER_OTP_SERVICE_BLOCKED.name(),
          ExceptionErrorMessages.USER_OTP_SERVICE_BLOCKED.getMessage(), HttpStatus.FORBIDDEN);
    }
  }

  @Override
  public void validateOtp(OtpDto otpDto) {
    OtpEntity otpEntity = otpRepository.findByOtpId(otpDto.getOtpId());

    if (utils.findDifference(otpEntity.getCreationTime(), new Date()) > Long
        .parseLong(appProperties.getProperty("blockDuration"))) {
      throw new OtpServiceException(ExceptionErrorCodes.OTP_CODE_EXPIRED.name(),
          ExceptionErrorMessages.OTP_CODE_EXPIRED.getMessage(), HttpStatus.OK);
    }
    if (!otpDto.getCode().equals(otpEntity.getCode())) {
      throw new OtpServiceException(ExceptionErrorCodes.OTP_CODE_MISMATCH.name(),
          ExceptionErrorMessages.OTP_CODE_MISMATCH.getMessage(), HttpStatus.OK);
    }

  }

}
