package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.FeeEntity;

@Repository
public interface FeeRepository extends PagingAndSortingRepository<FeeEntity, Long> {
  @Query("SELECT f FROM FeeEntity f WHERE f.user.userId=:userId")
  Page<FeeEntity> findByUserUserId(String userId, Pageable pageable);
}
