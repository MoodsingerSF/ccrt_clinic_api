package com.moodsinger.ccrt_clinic.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.model.request.UserSignupRequestModel;
import com.moodsinger.ccrt_clinic.model.request.UserUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.BlogRest;
import com.moodsinger.ccrt_clinic.model.response.UserRest;
import com.moodsinger.ccrt_clinic.service.UserBlogService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.BlogDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserBlogService userBlogService;

  @PostMapping
  public UserRest createUser(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    // check validity of request
    checkUserSignupRequestBody(userSignupRequestModel, false);

    // create user
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);

    // save user
    UserDto createdUserDto = userService.createUser(userDto);
    // returning created user
    UserRest userRest = modelMapper.map(createdUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping
  public UserRest getUser(@RequestParam("email") String email) {
    UserDto foundUserDto = userService.getUserByEmail(email);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping("/doctors")
  public List<UserRest> getDoctors(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit,
      @RequestParam(value = "status", required = false, defaultValue = "ACCEPTED") VerificationStatus verificationStatus) {
    List<UserDto> foundDoctors = userService.getDoctors(page, limit, verificationStatus);
    List<UserRest> foundDoctorsRest = new ArrayList<>();
    for (UserDto userDto : foundDoctors) {
      foundDoctorsRest.add(modelMapper.map(userDto, UserRest.class));
    }
    return foundDoctorsRest;
  }

  @GetMapping("/{userId}")
  public UserRest getUserDetails(@PathVariable String userId) {
    UserDto foundUserDto = userService.getUserByUserId(userId);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PutMapping("/{userId}")
  public UserRest updateUserDetails(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {

    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUser(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;

  }

  @PutMapping(path = "/{userId}/profile-picture", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public UserRest updateProfilePicture(@PathVariable String userId,
      @RequestPart("image") MultipartFile image) {
    UserDto createdUserDto = userService.updateProfilePicture(userId, image);
    UserRest createdUserRest = modelMapper.map(createdUserDto, UserRest.class);
    return createdUserRest;

  }

  @PutMapping("/{userId}/role")
  public UserRest updateUserRole(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {
    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUserRole(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PutMapping("/{userId}/verification-status")
  public UserRest updateUserVerificationStatus(@PathVariable String userId,
      @RequestBody UserUpdateRequestModel userUpdateRequestModel) {

    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUserVerificationStatus(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;
  }

  @PostMapping("/admin")
  public UserRest createAdmin(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    checkUserSignupRequestBody(userSignupRequestModel, true);
    userSignupRequestModel.setUserType(Role.ADMIN.name());
    // create user
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);
    // save user
    UserDto createdUserDto = userService.createUser(userDto);
    // returning created user
    UserRest userRest = modelMapper.map(createdUserDto, UserRest.class);
    return userRest;
  }

  @GetMapping("/{userId}/blogs")
  public List<BlogRest> getUserBlogs(@PathVariable String userId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "limit", defaultValue = "15", required = false) int limit) {
    List<BlogDto> blogDtos = userBlogService.getUserBlogs(userId, page, limit);
    List<BlogRest> blogRests = new ArrayList<>();
    for (BlogDto blogDto : blogDtos) {
      blogRests.add(modelMapper.map(blogDto, BlogRest.class));
    }
    return blogRests;
  }

  private void checkUserSignupRequestBody(UserSignupRequestModel userSignupRequestModel, boolean isUserTypeOptional) {
    String firstName = userSignupRequestModel.getFirstName();
    String lastName = userSignupRequestModel.getLastName();
    String email = userSignupRequestModel.getEmail();
    String password = userSignupRequestModel.getPassword();
    String userType = userSignupRequestModel.getUserType();

    if (!utils.isNonNullAndNonEmpty(firstName))
      throw new UserServiceException(ExceptionErrorCodes.FIRST_NAME_NOT_VALID.name(),
          ExceptionErrorMessages.FIRST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.isNonNullAndNonEmpty(lastName))
      throw new UserServiceException(ExceptionErrorCodes.LAST_NAME_NOT_VALID.name(),
          ExceptionErrorMessages.LAST_NAME_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validateEmail(email))
      throw new UserServiceException(ExceptionErrorCodes.EMAIL_NOT_VALID.name(),
          ExceptionErrorMessages.EMAIL_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!utils.validatePassword(password))
      throw new UserServiceException(ExceptionErrorCodes.PASSWORD_NOT_VALID.name(),
          ExceptionErrorMessages.PASSWORD_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
    if (!isUserTypeOptional && !utils.validateUserType(userType))
      throw new UserServiceException(ExceptionErrorCodes.USER_TYPE_NOT_VALID.name(),
          ExceptionErrorMessages.USER_TYPE_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
