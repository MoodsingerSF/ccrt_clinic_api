package com.moodsinger.ccrt_clinic.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moodsinger.ccrt_clinic.io.entity.RoleEntity;
import com.moodsinger.ccrt_clinic.io.enums.Role;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
  RoleEntity findByName(Role role);
}
