package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.RatingCriteriaEntity;

@Repository
public interface RatingCriteriaRepository extends CrudRepository<RatingCriteriaEntity, Long> {
  List<RatingCriteriaEntity> findAll();
}
