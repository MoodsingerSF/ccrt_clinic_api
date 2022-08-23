package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.OtpBlacklistEntity;

@Repository
public interface OtpBlacklistRepository extends CrudRepository<OtpBlacklistEntity, Long> {
  // @Query(value = "select * from otp_blacklist where
  // timestampdiff(minute,time_of_blocking,now()) <= :blockDuration and
  // email=:email", nativeQuery = true)
  // OtpBlacklistEntity findIfEmailIsBlacklistedOrNot(@Param("name") String email,
  // @Param("blockDuration") long blockDuration);
  List<OtpBlacklistEntity> findAllByEmail(String email);
}
