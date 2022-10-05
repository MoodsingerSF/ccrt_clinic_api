package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
  UserEntity findByUserId(String userId);

  UserEntity findByEmail(String email);

  @Query("Select u from UserEntity u join u.roles r where r.name=:role AND u.verificationStatus=:verificationStatus")
  Page<UserEntity> findByRoleAndVerificationStatus(@Param("role") Role role,
      @Param("verificationStatus") VerificationStatus verificationStatus, Pageable pageable);

  UserEntity findByResetPasswordToken(String token);

}
