package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.DayEntity;

@Repository
public interface DayRepository extends CrudRepository<DayEntity, Long> {
  List<DayEntity> findAll();
}
