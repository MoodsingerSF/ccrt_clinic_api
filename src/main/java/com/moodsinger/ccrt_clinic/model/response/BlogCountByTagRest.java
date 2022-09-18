package com.moodsinger.ccrt_clinic.model.response;

public class BlogCountByTagRest {
  private TagRest tag;
  private long count;

  public TagRest getTag() {
    return tag;
  }

  public void setTag(TagRest tag) {
    this.tag = tag;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
