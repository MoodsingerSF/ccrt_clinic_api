package com.moodsinger.ccrt_clinic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BlogRest createNewBlog(
      @ModelAttribute BlogCreationRequestModel blogCreationRequestModel) {

    BlogDto blogDetailsDto = modelMapper.map(blogCreationRequestModel,
        BlogDto.class);
    BlogDto createdBlogDto = blogService.createBlog(blogDetailsDto);
    BlogRest blogRest = modelMapper.map(createdBlogDto, BlogRest.class);
    return blogRest;
  }

  @GetMapping
  public List<BlogRest> getBlogs(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(name = "tag", required = false) String tag) {
    List<BlogRest> returnedBlogs = new ArrayList<>();
    List<BlogDto> currentPageBlogs;
    if (tag == null) {
      currentPageBlogs = blogService.getBlogs(page, limit);
    } else {
      currentPageBlogs = blogService.getBlogs(page, limit, tag);
    }

    for (BlogDto blog : currentPageBlogs) {
      returnedBlogs.add(modelMapper.map(blog, BlogRest.class));
    }
    return returnedBlogs;
  }

  @GetMapping(path = "/{blogId}")
  public BlogRest getBlogDetails(@PathVariable String blogId) {
    BlogDto blogDto = blogService.getBlog(blogId);
    BlogRest blogRest = modelMapper.map(blogDto, BlogRest.class);
    return blogRest;
  }

  @PutMapping(path = "/{blogId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public BlogRest updateBlogDetails(@PathVariable String blogId,
      @ModelAttribute BlogCreationRequestModel blogCreationRequestModel) {
    BlogDto blogDetails = modelMapper.map(blogCreationRequestModel, BlogDto.class);
    BlogDto blogDto = blogService.updateBlog(blogId, blogDetails);
    BlogRest blogRest = modelMapper.map(blogDto, BlogRest.class);
    return blogRest;
  }

  @DeleteMapping(path = "/{blogId}")
  public ResponseEntity<String> deleteBlog(@PathVariable String blogId) {
    blogService.deleteBlog(blogId);
    return new ResponseEntity<>("SUCCESS", HttpStatus.NO_CONTENT);
  }

  @GetMapping(path = "/{blogId}/related-blogs")
  public List<BlogRest> getRelatedBlogs(@PathVariable String blogId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<BlogDto> foundBlogDtos = blogService.getRelatedBlogs(blogId, page, limit);
    List<BlogRest> blogRests = new ArrayList<>();
    for (BlogDto blogDto : foundBlogDtos) {
      blogRests.add(modelMapper.map(blogDto, BlogRest.class));
    }
    return blogRests;
  }
}
