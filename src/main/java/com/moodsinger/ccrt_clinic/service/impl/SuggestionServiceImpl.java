package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.io.entity.SuggestionEntity;
import com.moodsinger.ccrt_clinic.io.repository.SuggestionRepository;
import com.moodsinger.ccrt_clinic.service.SuggestionService;
import com.moodsinger.ccrt_clinic.shared.dto.SuggestionDto;

@Service
public class SuggestionServiceImpl implements SuggestionService {

  @Autowired
  private SuggestionRepository suggestionRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public SuggestionDto createSuggestion(SuggestionDto suggestionDto) {
    SuggestionEntity suggestionEntity = modelMapper.map(suggestionDto, SuggestionEntity.class);
    SuggestionEntity createdSuggestionEntity = suggestionRepository.save(suggestionEntity);
    return modelMapper.map(createdSuggestionEntity, SuggestionDto.class);
  }

  @Override
  public List<SuggestionDto> getSuggestions(int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("creationTime").descending());
    Page<SuggestionEntity> suggestionPage = suggestionRepository.findAll(pageable);
    List<SuggestionEntity> suggestions = suggestionPage.getContent();
    List<SuggestionDto> suggestionDtos = new ArrayList<>();
    for (SuggestionEntity suggestionEntity : suggestions) {
      suggestionDtos.add(modelMapper.map(suggestionEntity, SuggestionDto.class));
    }
    return suggestionDtos;
  }

}
