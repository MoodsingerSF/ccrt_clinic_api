package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.exceptions.HomeCoverException;
import com.moodsinger.ccrt_clinic.exceptions.enums.MessageCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.Messages;
import com.moodsinger.ccrt_clinic.io.enums.VisibilityType;
import com.moodsinger.ccrt_clinic.model.request.HomeCoverCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.request.HomeCoverUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.HomeCoverRest;
import com.moodsinger.ccrt_clinic.service.HomeCoverService;
import com.moodsinger.ccrt_clinic.shared.dto.HomeCoverDto;

@RestController
@RequestMapping("/home-covers")
public class HomeCoverController {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HomeCoverService homeCoverService;

  @PostMapping
  public HomeCoverRest addHomeCover(@RequestBody HomeCoverCreationRequestModel homeCoverCreationRequestModel) {
    validateHomeCoverCreationRequest(homeCoverCreationRequestModel);
    HomeCoverDto homeCoverDto = modelMapper.map(homeCoverCreationRequestModel, HomeCoverDto.class);
    HomeCoverDto createdHomeCoverDto = homeCoverService.addNewHomeCover(homeCoverDto);
    HomeCoverRest homeCoverRest = modelMapper.map(createdHomeCoverDto, HomeCoverRest.class);
    return homeCoverRest;
  }

  @GetMapping
  public List<HomeCoverRest> retrieveHomeCovers(
      @RequestParam(name = "type", defaultValue = "VISIBLE", required = true) VisibilityType visibilityType) {
    List<HomeCoverDto> foundHomeCoverDtos = homeCoverService.retrieveHomeCovers(visibilityType);
    List<HomeCoverRest> homeCoverRests = new ArrayList<>();
    for (HomeCoverDto homeCoverDto : foundHomeCoverDtos) {
      homeCoverRests.add(modelMapper.map(homeCoverDto, HomeCoverRest.class));
    }
    return homeCoverRests;
  }

  @DeleteMapping("/{coverId}")
  public ResponseEntity<String> deleteHomeCover(@PathVariable(name = "coverId") String coverId) {
    homeCoverService.deleteCover(coverId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{coverId}")
  public HomeCoverRest updateHomeCoverVisibility(@PathVariable(name = "coverId") String coverId,
      @RequestBody HomeCoverUpdateRequestModel homeCoverUpdateRequestModel) {
    validateHomeCoverUpdateRequest(homeCoverUpdateRequestModel);
    HomeCoverDto updatedHomeCoverDto = homeCoverService.updateHomeCover(coverId,
        modelMapper.map(homeCoverUpdateRequestModel, HomeCoverDto.class));
    HomeCoverRest homeCoverRest = modelMapper.map(updatedHomeCoverDto, HomeCoverRest.class);
    return homeCoverRest;
  }

  private void validateHomeCoverCreationRequest(HomeCoverCreationRequestModel homeCoverCreationRequestModel) {
    if (homeCoverCreationRequestModel.getType() == null) {
      throw new HomeCoverException(MessageCodes.TYPE_NOT_VALID.name(), Messages.TYPE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
    if (homeCoverCreationRequestModel.getItemId() == null) {
      throw new HomeCoverException(MessageCodes.ITEM_ID_NOT_VALID.name(), Messages.ITEM_ID_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }
  }

  private void validateHomeCoverUpdateRequest(HomeCoverUpdateRequestModel homeCoverUpdateRequestModel) {
    if (homeCoverUpdateRequestModel.getVisibilityType() == null) {
      throw new HomeCoverException(MessageCodes.TYPE_NOT_VALID.name(), Messages.VISIBILITY_TYPE_NOT_VALID.getMessage(),
          HttpStatus.BAD_REQUEST);
    }

  }
}
