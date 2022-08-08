package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<BlogEntity, Long> {
  BlogEntity findByBlogId(String blogId);

  Page<BlogEntity> findAllByVerificationStatus(VerificationStatus verificationStatus, Pageable pageable);

  Page<BlogEntity> findByTagsNameAndVerificationStatus(String name, VerificationStatus verificationStatus,
      Pageable pageable);

  @Query("select distinct b from BlogEntity b join b.tags t where t.id in :tagIds and b.verificationStatus=:verificationStatus")
  Page<BlogEntity> findBlogsByTagList(@Param("tagIds") List<Long> tagIds,
      @Param("verificationStatus") VerificationStatus verificationStatus, Pageable pageable);
}
