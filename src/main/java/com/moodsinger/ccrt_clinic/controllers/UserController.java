package com.moodsinger.ccrt_clinic.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.model.request.UserSignupRequestModel;
import com.moodsinger.ccrt_clinic.model.request.UserUpdateRequestModel;
import com.moodsinger.ccrt_clinic.model.response.UserRest;
import com.moodsinger.ccrt_clinic.security.SecurityConstants;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @PostMapping
  public UserRest createUser(@RequestBody UserSignupRequestModel userSignupRequestModel) {
    System.out.println(userSignupRequestModel);
    // check validity of request
    checkUserSignupRequestBody(userSignupRequestModel);

    // create user
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = modelMapper.map(userSignupRequestModel, UserDto.class);

    // save user
    UserDto createdUserDto = userService.createUser(userDto);
    // returning created user
    UserRest userRest = modelMapper.map(createdUserDto, UserRest.class);
    return userRest;
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

    ModelMapper modelMapper = new ModelMapper();
    UserDto updateRequestDto = modelMapper.map(userUpdateRequestModel, UserDto.class);
    UserDto foundUserDto = userService.updateUser(userId, updateRequestDto);
    UserRest userRest = new ModelMapper().map(foundUserDto, UserRest.class);
    return userRest;

  }

  private void checkUserSignupRequestBody(UserSignupRequestModel userSignupRequestModel) {
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
    if (!utils.validateUserType(userType))
      throw new UserServiceException(ExceptionErrorCodes.USER_TYPE_NOT_VALID.name(),
          ExceptionErrorMessages.USER_TYPE_NOT_VALID.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
