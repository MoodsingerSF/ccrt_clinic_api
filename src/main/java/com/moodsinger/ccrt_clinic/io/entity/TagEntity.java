package com.moodsinger.ccrt_clinic.io.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class TagEntity {

  public TagEntity() {
  }

  public TagEntity(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH }, fetch = FetchType.LAZY, mappedBy = "tags")
  private List<BlogEntity> blogs = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<BlogEntity> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<BlogEntity> blogs) {
    this.blogs = blogs;
  }

  @Override
  public String toString() {
    return "TagEntity [blogs=" + blogs + ", id=" + id + ", name=" + name + "]";
  }
}
