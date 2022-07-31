package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.TagEntity;

@Repository
public interface TagRepository extends PagingAndSortingRepository<TagEntity, Long> {

}
