package com.moodsinger.ccrt_clinic.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.model.request.BlogCreationRequestModel;
import com.moodsinger.ccrt_clinic.model.response.BlogRest;
import com.moodsinger.ccrt_clinic.service.BlogService;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;

@RestController
@RequestMapping("blogs")
public class BlogController {

  @Autowired
  private BlogService blogService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BlogRest createNewBlog(
      @ModelAttribute BlogCreationRequestModel blogCreationRequestModel) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    System.out.println(blogCreationRequestModel);
    System.out.println(blogCreationRequestModel.getImage().getOriginalFilename());
    BlogDto blogDetailsDto = modelMapper.map(blogCreationRequestModel,
        BlogDto.class);
    System.out.println("blog details dto ==" + blogDetailsDto.getImage().getOriginalFilename());

    BlogDto createdBlogDto = blogService.createBlog(blogDetailsDto);
    BlogRest blogRest = modelMapper.map(createdBlogDto, BlogRest.class);
    return blogRest;
  }
}
