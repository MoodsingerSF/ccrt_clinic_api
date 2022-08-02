package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<BlogEntity, Long> {
  BlogEntity findByBlogId(String blogId);

  Page<BlogEntity> findByTagsName(String name, Pageable pageable);
}
