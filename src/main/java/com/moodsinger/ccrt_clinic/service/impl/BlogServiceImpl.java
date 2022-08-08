package com.moodsinger.ccrt_clinic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.AppProperties;
import com.moodsinger.ccrt_clinic.exceptions.BlogServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.BlogEntity;
import com.moodsinger.ccrt_clinic.io.entity.TagEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.io.repository.BlogRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.model.request.BlogVerificationStatusUpdateRequestModel;
import com.moodsinger.ccrt_clinic.service.BlogService;
import com.moodsinger.ccrt_clinic.service.TagService;
import com.moodsinger.ccrt_clinic.shared.FileUploadUtil;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private TagService tagService;

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private FileUploadUtil fileUploadUtil;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  @Override
  public BlogDto createBlog(BlogDto blogDto) {
    String blogId = utils.generateBlogId(15);
    String uploadDir = FileUploadUtil.BLOG_UPLOAD_DIR + File.separator + blogId;
    MultipartFile image = blogDto.getImage();
    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
    try {
      fileUploadUtil.saveFile(uploadDir, getFileName(fileName), image);
      BlogEntity blogEntity = modelMapper.map(blogDto, BlogEntity.class);

      blogEntity.setBlogId(blogId);
      blogEntity.setImageUrl(
          getImageUrl(blogId, fileName));
      UserEntity creatorEntity = userRepository.findByUserId(blogDto.getCreatorUserId());
      if (creatorEntity == null)
        throw new BlogServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
            ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.BAD_REQUEST);
      blogEntity.setCreator(creatorEntity);
      List<String> tags = blogDto.getTagStrings();
      Set<TagEntity> tagEntities = new HashSet<>();
      blogEntity.setTags(tagEntities);
      if (tags != null && tags.size() != 0) {
        for (String tag : tags) {
          TagEntity newTagEntity = tagService.getOrCreateTag(new TagDto(tag));
          blogEntity.getTags().add(newTagEntity);
          newTagEntity.getBlogs().add(blogEntity);
        }

      }

      BlogEntity createdBlogEntity = blogRepository.save(blogEntity);
      BlogDto createdBlogDto = modelMapper.map(createdBlogEntity, BlogDto.class);
      return createdBlogDto;
    } catch (IOException e) {
      throw new BlogServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
          ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      throw new BlogServiceException("", "");
    }
  }

  @Override
  public List<BlogDto> getBlogs(int page, int limit, VerificationStatus verificationStatus) {
    List<BlogDto> blogs = new ArrayList<>();
    Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
    Slice<BlogEntity> pageElements = blogRepository.findAllByVerificationStatus(verificationStatus, pageable);
    List<BlogEntity> blogEntities = pageElements.getContent();
    for (BlogEntity blogEntity : blogEntities) {
      blogs.add(modelMapper.map(blogEntity, BlogDto.class));
    }
    return blogs;
  }

  @Override
  public BlogDto getBlog(String blogId) {
    BlogEntity foundBlogEntity = blogRepository.findByBlogId(blogId);
    if (foundBlogEntity == null)
      throw new BlogServiceException(ExceptionErrorCodes.BLOG_NOT_FOUND.name(),
          ExceptionErrorMessages.BLOG_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    BlogDto blog = modelMapper.map(foundBlogEntity, BlogDto.class);
    return blog;
  }

  @Transactional
  @Override
  public BlogDto updateBlog(String blogId, BlogDto blogDetails) {
    BlogEntity foundBlogEntity = blogRepository.findByBlogId(blogId);
    if (foundBlogEntity == null)
      throw new BlogServiceException(ExceptionErrorCodes.BLOG_NOT_FOUND.name(),
          ExceptionErrorMessages.BLOG_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    System.out.println(blogDetails);
    if (!foundBlogEntity.getCreator().getUserId().equals(blogDetails.getCreatorUserId())) {
      throw new BlogServiceException(ExceptionErrorCodes.FORBIDDEN.name(),
          ExceptionErrorMessages.FORBIDDEN.getMessage(), HttpStatus.FORBIDDEN);
    }
    String title = blogDetails.getTitle();
    String description = blogDetails.getDescription();
    List<String> tags = blogDetails.getTagStrings();
    MultipartFile image = blogDetails.getImage();
    if (title != null)
      foundBlogEntity.setTitle(title);

    if (description != null)
      foundBlogEntity.setDescription(description);

    if (image != null) {
      try {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        fileUploadUtil.saveFile(FileUploadUtil.BLOG_UPLOAD_DIR + File.separator + blogId,
            getFileName(fileName), image);

        foundBlogEntity
            .setImageUrl(getImageUrl(blogId, fileName));

      } catch (Exception e) {
        throw new BlogServiceException(ExceptionErrorCodes.FILE_SAVE_ERROR.name(),
            ExceptionErrorMessages.FILE_SAVE_ERROR.getMessage());
      }
    }
    if (tags != null && !tags.isEmpty()) {
      Set<TagEntity> tagEntities = new HashSet<>();
      foundBlogEntity.setTags(tagEntities);
      if (tags != null && tags.size() != 0) {
        for (String tag : tags) {
          TagEntity newTagEntity = tagService.getOrCreateTag(new TagDto(tag));
          foundBlogEntity.getTags().add(newTagEntity);
          newTagEntity.getBlogs().add(foundBlogEntity);
        }

      }
    }
    BlogEntity updatedBlogEntity = blogRepository.save(foundBlogEntity);
    BlogDto updatedBlog = modelMapper.map(updatedBlogEntity, BlogDto.class);
    return updatedBlog;
  }

  private String getImageUrl(String blogId, String fileName) {
    return appProperties.getProperty("baseUrl") + "blogs/" + blogId + "/index." + utils.getFileExtension(fileName);
  }

  private String getFileName(String fileName) {
    return "index." + utils.getFileExtension(fileName);
  }

  @Override
  public List<BlogDto> getBlogs(int page, int limit, String tag) {

    Pageable pageable = PageRequest.of(page, limit);
    Page<BlogEntity> pageEntities = blogRepository.findByTagsNameAndVerificationStatus(tag, VerificationStatus.ACCEPTED,
        pageable);
    List<BlogEntity> blogEntities = pageEntities.getContent();
    List<BlogDto> blogs = new ArrayList<>();
    for (BlogEntity blogEntity : blogEntities) {
      blogs.add(modelMapper.map(blogEntity, BlogDto.class));
    }
    return blogs;
  }

  @Override
  public void deleteBlog(String blogId) {
    BlogEntity foundBlogEntity = blogRepository.findByBlogId(blogId);
    if (foundBlogEntity == null) {
      throw new BlogServiceException(ExceptionErrorCodes.BLOG_NOT_FOUND.name(),
          ExceptionErrorMessages.BLOG_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }

    blogRepository.delete(foundBlogEntity);

  }

  @Override
  public List<BlogDto> getRelatedBlogs(String blogId, int page, int limit) {
    BlogEntity foundBlogEntity = blogRepository.findByBlogId(blogId);
    if (foundBlogEntity == null) {
      throw new BlogServiceException(ExceptionErrorCodes.BLOG_NOT_FOUND.name(),
          ExceptionErrorMessages.BLOG_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    List<Long> tagIds = new ArrayList<>();
    for (TagEntity tagEntity : foundBlogEntity.getTags()) {
      tagIds.add(tagEntity.getId());
    }
    Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
    Page<BlogEntity> pageBlogEntities = blogRepository.findBlogsByTagList(tagIds, VerificationStatus.ACCEPTED,
        pageable);
    List<BlogEntity> blogEntities = pageBlogEntities.getContent();
    List<BlogDto> blogDtos = new ArrayList<>();
    for (BlogEntity blogEntity : blogEntities) {
      blogDtos.add(modelMapper.map(blogEntity, BlogDto.class));
    }
    return blogDtos;
  }

  @Override
  public BlogDto updateBlogVerificationStatus(String blogId,
      BlogVerificationStatusUpdateRequestModel blogVerificationStatusUpdateRequestModel) {
    BlogEntity foundBlogEntity = blogRepository.findByBlogId(blogId);
    if (foundBlogEntity == null) {
      throw new BlogServiceException(ExceptionErrorCodes.BLOG_NOT_FOUND.name(),
          ExceptionErrorMessages.BLOG_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    if (blogVerificationStatusUpdateRequestModel.getVerificationStatus().equals(VerificationStatus.ACCEPTED.name()))
      foundBlogEntity.setVerificationStatus(VerificationStatus.ACCEPTED);
    if (blogVerificationStatusUpdateRequestModel.getVerificationStatus().equals(VerificationStatus.REJECTED.name()))
      foundBlogEntity.setVerificationStatus(VerificationStatus.REJECTED);
    BlogEntity updatedBlogEntity = blogRepository.save(foundBlogEntity);
    BlogDto updatedBlogDto = modelMapper.map(updatedBlogEntity, BlogDto.class);
    return updatedBlogDto;
  }

}
