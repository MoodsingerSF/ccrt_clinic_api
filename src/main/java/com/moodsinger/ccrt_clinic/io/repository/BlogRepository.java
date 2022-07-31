package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<BlogEntity, Long> {

}
