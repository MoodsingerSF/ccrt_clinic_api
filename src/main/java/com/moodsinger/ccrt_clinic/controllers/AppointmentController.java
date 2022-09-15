package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.AppointmentCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.AppointmentRest;
import com.moodsinger.ccrt_clinic.service.AppointmentService;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;

@RestController
@RequestMapping("slots/{slotId}/appointments")
public class AppointmentController {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AppointmentService appointmentService;

  @PostMapping
  public AppointmentRest makeAppointment(@PathVariable(name = "slotId") String slotId,
      @RequestBody AppointmentCreationRequestModel appointmentCreationRequestModel) {
    AppointmentDto appointmentDto = modelMapper.map(appointmentCreationRequestModel, AppointmentDto.class);
    appointmentDto.setSlotId(slotId);
    AppointmentDto createdAppointmentDto = appointmentService.createAppointment(appointmentDto);
    AppointmentRest appointmentRest = modelMapper.map(createdAppointmentDto, AppointmentRest.class);
    return appointmentRest;
  }

  @GetMapping
  public List<AppointmentRest> getAppointments(@PathVariable(name = "slotId") String slotId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<AppointmentDto> appointmentDtos = appointmentService.getAppointments(slotId, page, limit);
    List<AppointmentRest> appointmentRests = new ArrayList<>();
    for (AppointmentDto appointmentDto : appointmentDtos) {
      appointmentRests.add(modelMapper.map(appointmentDto, AppointmentRest.class));
    }
    return appointmentRests;
  }
}
