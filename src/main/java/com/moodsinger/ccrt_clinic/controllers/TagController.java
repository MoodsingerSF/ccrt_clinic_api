package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.service.TagService;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

@RestController
@RequestMapping("tags")
public class TagController {
  @Autowired
  private TagService tagService;

  @GetMapping(path = "/search")
  public List<String> searchTagsByPrefix(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "prefix", defaultValue = "", required = true) String prefix) {
    List<TagDto> foundTags = tagService.searchTagsByPrefix(prefix, page, limit);
    List<String> tags = new ArrayList<>();
    for (TagDto tagDto : foundTags) {
      tags.add(tagDto.getName());
    }
    return tags;
  }
}
