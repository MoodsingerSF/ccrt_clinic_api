package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.response.SpecializationRest;
import com.moodsinger.ccrt_clinic.service.SpecializationService;
import com.moodsinger.ccrt_clinic.shared.dto.SpecializationDto;

@RestController
@RequestMapping("specializations")
public class SpecializationController {
  @Autowired
  private SpecializationService specializationService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  private List<SpecializationRest> retrieveSpecializations(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<SpecializationDto> specializationDtos = specializationService.retrieveSpecializations(page, limit);
    List<SpecializationRest> specializationRests = new ArrayList<>();
    for (SpecializationDto specializationDto : specializationDtos) {
      specializationRests.add(modelMapper.map(specializationDto, SpecializationRest.class));
    }
    return specializationRests;
  }

  @GetMapping("/search")
  private List<SpecializationRest> searchSpecializationByPrefix(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "prefix", defaultValue = "", required = true) String prefix) {

    List<SpecializationDto> specializationDtos = specializationService.searchByPrefix(prefix, page, limit);
    List<SpecializationRest> specializationRests = new ArrayList<>();
    for (SpecializationDto specializationDto : specializationDtos) {
      specializationRests.add(modelMapper.map(specializationDto, SpecializationRest.class));
    }
    return specializationRests;
  }
}
