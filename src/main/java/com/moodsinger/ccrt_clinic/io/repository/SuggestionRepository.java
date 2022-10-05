package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.SuggestionEntity;

@Repository
public interface SuggestionRepository extends PagingAndSortingRepository<SuggestionEntity, Long> {
  Page<SuggestionEntity> findAll(Pageable pageable);
}
