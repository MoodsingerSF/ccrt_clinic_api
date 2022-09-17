package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.RatingEntity;

@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Long> {
  // List<RatingEntity> saveAll(List<RatingEntity> ratingEntities);
}
