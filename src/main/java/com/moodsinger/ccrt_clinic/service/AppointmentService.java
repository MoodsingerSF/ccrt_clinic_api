package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.model.request.AppointmentCancelRequestModel;
import com.moodsinger.ccrt_clinic.model.request.AppointmentEndRequestModel;
import com.moodsinger.ccrt_clinic.shared.dto.AppointmentDto;
import com.moodsinger.ccrt_clinic.shared.dto.PrescriptionDto;
import com.moodsinger.ccrt_clinic.shared.dto.ResourceDto;

public interface AppointmentService {
    public AppointmentDto createAppointment(AppointmentDto appointmentDto);

    public List<AppointmentDto> getAppointments(String slotId, int page, int limit);

    public AppointmentDto getAppointmentDetails(String appointmentId);

    public AppointmentDto endAppointment(String appointmentId,
            AppointmentEndRequestModel appointmentEndRequestModel);

    public ResourceDto addResource(String appointmentId, String title, MultipartFile image);

    public AppointmentDto cancelAppointment(String appointmentId,
            AppointmentCancelRequestModel appointmentCancelRequestModel);

    public List<ResourceDto> getAppointmentResources(String appointmentId);

    public ResourceDto updateResource(String appointmentId, String resourceId, String userId,
            MultipartFile image);

    public void createPrescription(String appointmentId, PrescriptionDto prescriptionDto);

    public PrescriptionDto retrievePrescription(String appointmentId);

}
