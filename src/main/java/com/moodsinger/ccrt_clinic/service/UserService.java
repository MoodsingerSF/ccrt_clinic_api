package com.moodsinger.ccrt_clinic.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Service
public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDetails);

  UserDto getUserByUserId(String userId);

  UserDto getUserByEmail(String userId);

  UserDto updateUser(String userId, UserDto updateDetails);
}
