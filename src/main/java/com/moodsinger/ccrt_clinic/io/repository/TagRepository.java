package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.BlogCountByTag;
import com.moodsinger.ccrt_clinic.io.entity.TagEntity;

@Repository
public interface TagRepository extends PagingAndSortingRepository<TagEntity, Long> {
  TagEntity findByName(String name);

  Page<TagEntity> findByNameStartsWithIgnoreCase(String prefix, Pageable pageable);

  @Query("SELECT MAX(id) FROM BlogEntity")
  long getMaxId();

  @Query("SELECT new com.moodsinger.ccrt_clinic.io.entity.BlogCountByTag(t,count(*) AS totalBlogs) FROM BlogEntity b JOIN b.tags t WHERE b.id>=:startId GROUP BY t ORDER BY totalBlogs DESC")
  Page<BlogCountByTag> findPopularTags(@Param("startId") long startId, Pageable pageable);

}
