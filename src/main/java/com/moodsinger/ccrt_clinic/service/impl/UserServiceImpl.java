package com.moodsinger.ccrt_clinic.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.exceptions.UserServiceException;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorCodes;
import com.moodsinger.ccrt_clinic.exceptions.enums.ExceptionErrorMessages;
import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.entity.UserEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.enums.VerificationStatus;
import com.moodsinger.ccrt_clinic.io.repository.UserRepository;
import com.moodsinger.ccrt_clinic.service.RoleService;
import com.moodsinger.ccrt_clinic.service.UserService;
import com.moodsinger.ccrt_clinic.shared.Utils;
import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;
import com.moodsinger.ccrt_clinic.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private Utils utils;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  @Override
  public UserDto createUser(UserDto userDetails) {
    ModelMapper modelMapper = new ModelMapper();
    // crate user entity
    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    // adding public user id
    userEntity.setUserId(utils.generateUserId(30));
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

    System.out.println("----------------" + userDetails.getUserType() + "------------------");
    Set<RoleEntity> roles = new HashSet<>();
    RoleEntity role = modelMapper.map(
        roleService.getOrCreateRole(userDetails.getUserType().equals(Role.ADMIN.name()) ? Role.ADMIN
            : userDetails.getUserType().equals(Role.DOCTOR.name()) ? Role.DOCTOR : Role.USER),
        RoleEntity.class);
    roles.add(role);
    // https://youtube.com/clip/UgkxSEHC1SCU_h5ppeoKllC4GFT9GNfvezxr
    userEntity.setRoles(roles);

    // save user entity
    UserEntity createdUserEntity = userRepository.save(userEntity);

    if (createdUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_CREATED.name(),
          ExceptionErrorMessages.USER_NOT_CREATED.getMessage());

    UserDto userDto = modelMapper.map(createdUserEntity, UserDto.class);
    return userDto;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    UserDto userDto = new ModelMapper().map(foundUserEntity, UserDto.class);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUser(String userId, UserDto updateDetails) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    if (utils.isNonNullAndNonEmpty(updateDetails.getFirstName()))
      foundUserEntity.setFirstName(updateDetails.getFirstName());
    if (utils.isNonNullAndNonEmpty(updateDetails.getLastName()))
      foundUserEntity.setLastName(updateDetails.getLastName());

    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity foundUserEntity = userRepository.findByEmail(email);
    if (foundUserEntity == null)
      throw new UsernameNotFoundException(email);
    Set<RoleEntity> roles = foundUserEntity.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (RoleEntity role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName().name()));
    }
    return new User(foundUserEntity.getEmail(), foundUserEntity.getEncryptedPassword(), authorities);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity foundUserEntity = userRepository.findByEmail(email);
    if (foundUserEntity == null)
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    UserDto userDto = new ModelMapper().map(foundUserEntity, UserDto.class);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUserRole(String userId, UserDto updateDetails) {
    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    Role role = Role.valueOf(updateDetails.getRole());
    if (role == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_TYPE_NOT_VALID.name(),
          ExceptionErrorMessages.USER_TYPE_NOT_VALID.getMessage());
    }
    Set<RoleEntity> roles = new HashSet<>();
    RoleDto newRoleDto = roleService.getOrCreateRole(role);
    RoleEntity newRoleEntity = new ModelMapper().map(newRoleDto, RoleEntity.class);
    roles.add(newRoleEntity);
    foundUserEntity.setRoles(roles);
    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
    return userDto;
  }

  @Transactional
  @Override
  public UserDto updateUserVerificationStatus(String userId, UserDto updateDetails) {
    if (!utils.validateVerificationStatus(updateDetails.getVerificationStatus()))
      throw new UserServiceException(ExceptionErrorCodes.MALFORMED_JSON_BODY.name(),
          ExceptionErrorMessages.MALFORMED_JSON_BODY.getMessage(), HttpStatus.BAD_REQUEST);

    UserEntity foundUserEntity = userRepository.findByUserId(userId);
    if (foundUserEntity == null) {
      throw new UserServiceException(ExceptionErrorCodes.USER_NOT_FOUND.name(),
          ExceptionErrorMessages.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
    }
    foundUserEntity.setVerificationStatus(VerificationStatus.valueOf(updateDetails.getVerificationStatus()));
    UserEntity updatedUserEntity = userRepository.save(foundUserEntity);
    UserDto userDto = new ModelMapper().map(updatedUserEntity, UserDto.class);
    return userDto;
  }

}
