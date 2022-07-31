package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.OtpBlacklistEntity;

@Repository
public interface OtpBlacklistRepository extends CrudRepository<OtpBlacklistEntity, Long> {

}
