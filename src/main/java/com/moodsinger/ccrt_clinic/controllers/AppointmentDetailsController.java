package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.model.request.AppointmentCancelRequestModel;
import com.moodsinger.ccrt_clinic.model.request.AppointmentEndRequestModel;
import com.moodsinger.ccrt_clinic.model.request.AppointmentPrescriptionViewCodeRequestModel;
import com.moodsinger.ccrt_clinic.model.request.PrescriptionCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.ResourceRest;
import com.moodsinger.ccrt_clinic.model.response.AppointmentRest;
import com.moodsinger.ccrt_clinic.model.response.PrescriptionRest;
import com.moodsinger.ccrt_clinic.service.AppointmentService;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.PrescriptionDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;

@RestController
@RequestMapping("appointments/{appointmentId}")
public class AppointmentDetailsController {

  @Autowired
  private AppointmentService appointmentService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public AppointmentRest getAppointmentDetails(@PathVariable String appointmentId) {
    AppointmentDto appointmentDto = appointmentService.getAppointmentDetails(appointmentId);
    return modelMapper.map(appointmentDto, AppointmentRest.class);
  }

  @PostMapping("end")
  public AppointmentRest endAppointment(@PathVariable String appointmentId,
      @RequestBody AppointmentEndRequestModel appointmentEndRequestModel) {
    AppointmentDto appointmentDto = appointmentService.endAppointment(appointmentId, appointmentEndRequestModel);
    return modelMapper.map(appointmentDto, AppointmentRest.class);
  }

  @PostMapping("cancel")
  public AppointmentRest cancelAppointment(@PathVariable String appointmentId,
      @RequestBody AppointmentCancelRequestModel appointmentCancelRequestModel) {
    AppointmentDto appointmentDto = appointmentService.cancelAppointment(appointmentId, appointmentCancelRequestModel);
    return modelMapper.map(appointmentDto, AppointmentRest.class);
  }

  @PostMapping("resources")
  public ResourceRest addResource(@RequestPart(value = "image", required = true) MultipartFile image,
      @RequestPart(value = "title", required = true) String title, @PathVariable String appointmentId) {
    return modelMapper.map(appointmentService.addResource(appointmentId, title, image), ResourceRest.class);

  }

  @GetMapping("resources")
  public List<ResourceRest> getAppointmentResources(@PathVariable String appointmentId) {
    List<ResourceDto> appointmentResources = appointmentService.getAppointmentResources(appointmentId);
    List<ResourceRest> appointmentResourceRests = new ArrayList<>();
    for (ResourceDto resourceDto : appointmentResources) {
      appointmentResourceRests.add(modelMapper.map(resourceDto, ResourceRest.class));
    }

    return appointmentResourceRests;
  }

  @PutMapping("resources/{resourceId}")
  public ResourceRest updateResource(@PathVariable(name = "appointmentId") String appointmentId,
      @PathVariable(name = "resourceId") String resourceId,
      @RequestPart(name = "image", required = true) MultipartFile image,
      @RequestPart(name = "userId", required = true) String userId) {
    ResourceDto resourceDto = appointmentService.updateResource(appointmentId, resourceId, userId,
        image);
    return modelMapper.map(resourceDto, ResourceRest.class);
  }

  @PostMapping("prescription")
  public void createPrescription(@PathVariable String appointmentId,
      @RequestBody PrescriptionCreationRequestModel prescriptionCreationRequestModel) {
    PrescriptionDto prescriptionDto = modelMapper.map(prescriptionCreationRequestModel, PrescriptionDto.class);
    appointmentService.createPrescription(appointmentId, prescriptionDto);
  }

  @GetMapping("prescription")
  public PrescriptionRest getPrescription(@PathVariable String appointmentId) {
    PrescriptionDto prescriptionDto = appointmentService.retrievePrescription(appointmentId);
    return modelMapper.map(prescriptionDto, PrescriptionRest.class);
  }

  @PostMapping("prescription-view-code")
  public ResponseEntity<String> checkValidityOfPrescriptionViewCode(@PathVariable String appointmentId,
      @RequestBody AppointmentPrescriptionViewCodeRequestModel appointmentPrescriptionViewCodeRequestModel) {
    AppointmentDto appointmentDto = modelMapper.map(appointmentPrescriptionViewCodeRequestModel, AppointmentDto.class);
    appointmentDto.setAppointmentId(appointmentId);
    appointmentService.validatePrescriptionViewCode(appointmentDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
