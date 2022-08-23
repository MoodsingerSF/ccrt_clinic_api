package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.OtpEntity;

@Repository
public interface OtpRepository extends CrudRepository<OtpEntity, Long> {
  // @Query("select count(*) from otp where email=:email AND
  // TIMESTAMPDIFF(MINUTE,creation_time,now())<=10")
  // long getNumberOfOtpCodesSent(String email);
  List<OtpEntity> findAllByEmail(String email);

  OtpEntity findByOtpId(String otpId);
}
