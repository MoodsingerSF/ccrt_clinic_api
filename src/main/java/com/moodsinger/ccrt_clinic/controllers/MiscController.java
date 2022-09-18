package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.response.UserRest;
import com.moodsinger.ccrt_clinic.service.MiscService;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@RestController
@RequestMapping("misc")
public class MiscController {

  @Autowired
  private MiscService miscService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/popular-doctors")
  public List<UserRest> getPopularDoctors(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<UserDto> popularDoctors = miscService.findPopularDoctors(page, limit);
    List<UserRest> popularDoctorRests = new ArrayList<>();
    for (UserDto userDto : popularDoctors) {
      popularDoctorRests.add(modelMapper.map(userDto, UserRest.class));
    }
    return popularDoctorRests;
  }
}
