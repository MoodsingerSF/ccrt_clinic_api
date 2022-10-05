package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.PopularSpecialization;
import com.moodsinger.ccrt_clinic.io.entity.SpecializationEntity;

@Repository
public interface SpecializationRepository extends PagingAndSortingRepository<SpecializationEntity, Long> {
  SpecializationEntity findByName(String name);

  Page<SpecializationEntity> findAllByNameStartsWithIgnoreCase(String name, Pageable pageable);

  @Query("SELECT new com.moodsinger.ccrt_clinic.io.entity.PopularSpecialization(s.name,COUNT(*) as totalAppointments,s.id) FROM AppointmentEntity a INNER JOIN a.doctor d INNER JOIN d.specializations s GROUP BY s ORDER BY COUNT(*) DESC")
  Page<PopularSpecialization> findPopularSpecializations(Pageable pageable);

  Page<SpecializationEntity> findAll(Pageable pageable);

}
