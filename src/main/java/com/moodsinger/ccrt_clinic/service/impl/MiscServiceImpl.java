package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.io.entity.DoctorAppointmentCount;
import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;
import com.moodsinger.ccrt_clinic.io.repository.AppointmentRepository;
import com.moodsinger.ccrt_clinic.service.BlogService;
import com.moodsinger.ccrt_clinic.service.MiscService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Service
public class MiscServiceImpl implements MiscService {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BlogService blogService;

  @Transactional
  @Override
  public List<UserDto> findPopularDoctors(int page, int limit, int specializationId) {
    Page<DoctorAppointmentCount> popularDoctorsPage;
    if (specializationId <= 0) {
      popularDoctorsPage = appointmentRepository.findPopularDoctors(
          AppointmentStatus.FINISHED,
          PageRequest.of(page, limit));
    } else {
      popularDoctorsPage = appointmentRepository.findPopularDoctors(
          specializationId,
          AppointmentStatus.PENDING,
          PageRequest.of(page, limit));
    }
    List<DoctorAppointmentCount> popularDoctors = popularDoctorsPage.getContent();
    List<UserDto> popularDoctorsDtos = new ArrayList<>();
    for (DoctorAppointmentCount item : popularDoctors) {
      popularDoctorsDtos.add(userService.getUserByUserId(item.getUserId()));
    }
    return popularDoctorsDtos;
  }

  @Transactional
  @Override
  public List<UserDto> searchDoctors(int page, int limit, String keyword) {
    return userService.searchDoctorsByName(keyword, page, limit);
  }

  @Transactional
  @Override
  public List<BlogDto> searchBlogs(int page, int limit, String keyword) {
    return blogService.searchBlogsByTitleAndTags(keyword, page, limit);

  }

}
