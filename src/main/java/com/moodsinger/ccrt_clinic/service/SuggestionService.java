package com.moodsinger.ccrt_clinic.service;

import java.util.List;

import com.moodsinger.ccrt_clinic.shared.dto.SuggestionDto;

public interface SuggestionService {
  SuggestionDto createSuggestion(SuggestionDto suggestionDto);

  List<SuggestionDto> getSuggestions(int page, int limit);

}
