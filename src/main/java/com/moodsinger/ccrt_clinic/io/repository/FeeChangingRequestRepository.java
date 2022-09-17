package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.FeeChangingRequestEntity;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Repository
public interface FeeChangingRequestRepository extends PagingAndSortingRepository<FeeChangingRequestEntity, Long> {

  Page<FeeChangingRequestEntity> findAllByUserUserIdAndStatus(String userId, VerificationStatus status,
      Pageable pageable);

  Page<FeeChangingRequestEntity> findByStatus(VerificationStatus status, Pageable pageable);

  Page<FeeChangingRequestEntity> findAll(Pageable pageable);

  FeeChangingRequestEntity findByRequestId(String requestId);

}
