package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.SuggestionCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.SuggestionRest;
import com.moodsinger.ccrt_clinic.service.SuggestionService;
import com.moodsinger.ccrt_clinic.shared.dto.SuggestionDto;

@RestController
@RequestMapping("suggestions")
public class SuggestionController {
  @Autowired
  private SuggestionService suggestionService;
  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public SuggestionRest createSuggestion(@RequestBody SuggestionCreationRequestModel suggestionCreationRequestModel) {
    SuggestionDto suggestionDto = suggestionService
        .createSuggestion(modelMapper.map(suggestionCreationRequestModel, SuggestionDto.class));
    return modelMapper.map(suggestionDto, SuggestionRest.class);
  }

  @GetMapping
  public List<SuggestionRest> getSuggestions(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<SuggestionDto> suggestionDtos = suggestionService.getSuggestions(page, limit);
    List<SuggestionRest> suggestionRests = new ArrayList<>();
    for (SuggestionDto suggestionDto : suggestionDtos) {
      suggestionRests.add(modelMapper.map(suggestionDto, SuggestionRest.class));
    }
    return suggestionRests;
  }
}
