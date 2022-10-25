package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.HomeCoverEntity;

@Repository
public interface HomeCoverRepository extends PagingAndSortingRepository<HomeCoverEntity, Long> {
  List<HomeCoverEntity> findAllByIsVisible(boolean isVisible, Sort sort);

  HomeCoverEntity findByCoverId(String coverId);
}
