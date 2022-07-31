package com.moodsinger.ccrt_clinic.service;

import com.moodsinger.ccrt_clinic.io.enums.Role;
import com.moodsinger.ccrt_clinic.shared.dto.RoleDto;

public interface RoleService {
  RoleDto createRole(Role role);

  RoleDto getOrCreateRole(Role role);
}
