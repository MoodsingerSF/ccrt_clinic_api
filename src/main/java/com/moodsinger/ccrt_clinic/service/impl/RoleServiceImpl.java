package com.moodsinger.ccrt_clinic.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.io.repository.RoleRepository;
import com.moodsinger.ccrt_clinic.service.RoleService;
import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public RoleDto createRole(Role role) {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName(role);
    RoleEntity createdRole = roleRepository.save(roleEntity);
    RoleDto roleDto = new ModelMapper().map(createdRole, RoleDto.class);
    return roleDto;
  }

  @Transactional
  @Override
  public RoleDto getOrCreateRole(Role role) {
    RoleEntity foundRoleEntity = roleRepository.findByName(role);
    if (foundRoleEntity == null) {
      RoleEntity newRole = new RoleEntity();
      newRole.setName(role);
      foundRoleEntity = roleRepository.save(newRole);
    }
    RoleDto roleDto = new ModelMapper().map(foundRoleEntity, RoleDto.class);
    return roleDto;
  }

}
