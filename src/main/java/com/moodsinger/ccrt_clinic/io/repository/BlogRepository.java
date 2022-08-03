package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<BlogEntity, Long> {
  BlogEntity findByBlogId(String blogId);

  Page<BlogEntity> findByTagsName(String name, Pageable pageable);

  @Query("select distinct b from BlogEntity b join b.tags t where t.id in :tagIds")
  Page<BlogEntity> findBlogsByTagList(@Param("tagIds") List<Long> tagIds, Pageable pageable);
}
