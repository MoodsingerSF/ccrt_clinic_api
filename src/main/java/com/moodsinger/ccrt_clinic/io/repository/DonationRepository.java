package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.DonationEntity;

@Repository
public interface DonationRepository extends PagingAndSortingRepository<DonationEntity, Long> {
  Page<DonationEntity> findAll(Pageable pageable);

  Page<DonationEntity> findAllByDonorUserId(String userId, Pageable pageable);

  Page<DonationEntity> findAllByDonationRequestRequestId(String requestId, Pageable pageable);

}
