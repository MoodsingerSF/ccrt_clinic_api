package com.moodsinger.ccrt_clinic.service;

import java.util.Date;
import java.util.List;

import com.moodsinger.ccrt_clinic.io.enums.AppointmentStatus;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;

public interface UserAppointmentService {
  public List<AppointmentDto> getUserAppointments(String userId, int page, int limit, AppointmentStatus status,
      Date date);

  // public AppointmentDto cancelAppointment(String userId, String appointmentId);
}
