package com.moodsinger.ccrt_clinic.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.SlotCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.SlotVisibilityUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.SlotListRest;
import com.moodsinger.ccrt_clinic.model.response.SlotRest;
import com.moodsinger.ccrt_clinic.service.DoctorScheduleService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.SlotDto;

@RestController
@RequestMapping("/doctors/{userId}/schedule")
public class DoctorScheduleController {
  @Autowired
  private DoctorScheduleService doctorScheduleService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private Utils utils;

  @PostMapping
  public List<SlotRest> addSlot(@RequestBody SlotCreationRequestModel slotCreationRequestModel) throws ParseException {
    // System.out.println(slotCreationRequestModel);
    SlotDto slotDto = modelMapper.map(slotCreationRequestModel, SlotDto.class);
    slotDto.setStartTime(utils.getDateFromTimeString(slotCreationRequestModel.getStartTimeString()));
    slotDto.setEndTime(utils.getDateFromTimeString(slotCreationRequestModel.getEndTimeString()));

    List<SlotDto> newSlotDtos = doctorScheduleService.addSlot(slotDto);
    List<SlotRest> slotRests = new ArrayList<>();
    for (SlotDto slot : newSlotDtos) {
      slotRests.add(modelMapper.map(slot, SlotRest.class));
    }
    return slotRests;
  }

  @GetMapping
  public SlotListRest getAllSlots(@PathVariable(name = "userId") String userId,
      @RequestParam(name = "status", defaultValue = "all", required = false) String status) {
    if (status.equals("active"))
      return doctorScheduleService.retrieveActiveSlots(userId);
    return doctorScheduleService.retrieveSlots(userId);
  }

  @PutMapping("/{slotId}")
  public SlotRest updateSlotVisibility(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "slotId") String slotId,
      @RequestBody SlotVisibilityUpdateRequestModel slotVisibilityUpdateRequestModel) {
    System.out.println(slotVisibilityUpdateRequestModel);
    return modelMapper.map(doctorScheduleService.updateSlotVisibility(userId, slotId,
        modelMapper.map(slotVisibilityUpdateRequestModel, SlotDto.class)), SlotRest.class);
  }

}
