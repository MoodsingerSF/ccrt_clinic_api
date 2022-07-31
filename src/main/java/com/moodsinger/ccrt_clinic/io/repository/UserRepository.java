package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByUserId(String userId);

  UserEntity findByEmail(String email);

}
