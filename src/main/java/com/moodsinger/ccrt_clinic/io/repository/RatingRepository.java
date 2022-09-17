package com.moodsinger.ccrt_clinic.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.AverageRating;
import com.moodsinger.ccrt_clinic.io.entity.RatingEntity;

@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Long> {
  List<RatingEntity> findAllByPatientUserIdAndDoctorUserId(String doctorUserId, String patientUserId);

  @Query("SELECT new com.moodsinger.ccrt_clinic.io.entity.AverageRating(r.ratingCriteria, AVG(r.rating),r.doctor) FROM RatingEntity AS r WHERE r.doctor.userId=:doctorUserId GROUP BY r.ratingCriteria,r.doctor ")
  List<AverageRating> getAverageRatingOfDoctor(@Param("doctorUserId") String doctorUserId);

  RatingEntity findByRatingCriteriaIdAndDoctorUserIdAndPatientUserId(long ratingCriteriaId, String doctorUserId,
      String patientUserId);

}
