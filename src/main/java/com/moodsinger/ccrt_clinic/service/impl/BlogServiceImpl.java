package com.moodsinger.ccrt_clinic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.moodsinger.ccrt_clinic.io.repository.BlogRepository;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.BlogService;
import com.moodsinger.ccrt_clinic.service.TagService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.FileUploadUtil;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.TagDto;

@Service
public class BlogServiceImpl implements BlogService {

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private UserService userService;

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

  @Transactional
  @Override
  public BlogDto createBlog(BlogDto blogDto) {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    String blogId = utils.generateBlogId(15);
    String uploadDir = "blogs" + File.separator + blogId;
    MultipartFile image = blogDto.getImage();
    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
    try {
      fileUploadUtil.saveFile(uploadDir, fileName, image);
      BlogEntity blogEntity = modelMapper.map(blogDto, BlogEntity.class);

      blogEntity.setBlogId(blogId);
      blogEntity.setImageUrl(appProperties.getProperty("baseUrl") + "blogs/" + blogId + "/" + fileName);
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

}
