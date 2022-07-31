package com.moodsinger.ccrt_clinic.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.moodsinger.ccrt_clinic.io.enums.Role;

@Entity
@Table(name = "roles")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private Role name;

  // @ManyToMany(mappedBy = "roles")
  // Set<UserEntity> users;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Role getName() {
    return name;
  }

  public void setName(Role name) {
    this.name = name;
  }

}
