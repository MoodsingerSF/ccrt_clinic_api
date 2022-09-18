package com.moodsinger.ccrt_clinic.io.entity;

public class BlogCountByTag {
  private TagEntity tag;
  private long count;

  public BlogCountByTag() {
  }

  public BlogCountByTag(TagEntity tag, long count) {
    this.tag = tag;
    this.count = count;
  }

  public TagEntity getTag() {
    return tag;
  }

  public void setTag(TagEntity tag) {
    this.tag = tag;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "BlogCountByTag [count=" + count + ", tag=" + tag + "]";
  }
}
