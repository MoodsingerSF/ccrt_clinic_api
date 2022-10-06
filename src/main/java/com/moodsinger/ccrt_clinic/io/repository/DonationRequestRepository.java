package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.DonationRequestEntity;
import com.moodsinger.ccrt_clinic.io.enums.CompletionStatus;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Repository
public interface DonationRequestRepository extends PagingAndSortingRepository<DonationRequestEntity, Long> {
  Page<DonationRequestEntity> findAllByRequestStatusAndCompletionStatus(VerificationStatus requestStatus,
      CompletionStatus completionStatus,
      Pageable pageable);

  DonationRequestEntity findByRequestId(String requestId);

  Page<DonationRequestEntity> findAllByRequestorUserId(String userId,
      Pageable pageable);

}
