package com.moodsinger.ccrt_clinic.shared.dto;

import com.moodsinger.ccrt_clinic.io.enums.Role;

public class RoleDto {
  private int id;
  private Role name;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Role getName() {
    return name;
  }

  public void setName(Role name) {
    this.name = name;
  }

}
