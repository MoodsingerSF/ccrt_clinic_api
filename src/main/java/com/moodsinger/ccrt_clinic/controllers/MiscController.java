package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.moodsinger.ccrt_clinic.exceptions.model.ResponseMessage;
import com.moodsinger.ccrt_clinic.model.response.SpecializationRest;
import com.moodsinger.ccrt_clinic.model.response.UserRest;
import com.moodsinger.ccrt_clinic.service.MiscService;
import com.moodsinger.ccrt_clinic.service.SpecializationService;
import com.moodsinger.ccrt_clinic.shared.dto.SpecializationDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@RestController
@RequestMapping("misc")
public class MiscController {

  @Autowired
  private MiscService miscService;

  @Autowired
  private SpecializationService specializationService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/popular-doctors")
  public List<UserRest> getPopularDoctors(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "specialization", defaultValue = "-1", required = false) int specializationId) {
    List<UserDto> popularDoctors = miscService.findPopularDoctors(page, limit, specializationId);
    List<UserRest> popularDoctorRests = new ArrayList<>();
    for (UserDto userDto : popularDoctors) {
      popularDoctorRests.add(modelMapper.map(userDto, UserRest.class));
    }
    return popularDoctorRests;
  }

  @GetMapping("/popular-specializations")
  private List<SpecializationRest> getPopularSpecializations(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "specialization", defaultValue = "-1", required = false) int specializationId) {
    List<SpecializationDto> specializationDtos = specializationService.retrievePopularSpecializations(page, limit);
    List<SpecializationRest> specializationRests = new ArrayList<>();
    for (SpecializationDto specializationDto : specializationDtos) {
      specializationRests.add(modelMapper.map(specializationDto, SpecializationRest.class));
    }
    return specializationRests;
  }

}
