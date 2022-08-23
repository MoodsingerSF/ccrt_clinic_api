package com.moodsinger.ccrt_clinic.model.response;

import java.util.List;

public class BlogListRest {
  public BlogListRest() {
  }

  public BlogListRest(List<BlogRest> blogs, long totalBlogs, long page, long limit) {
    this.blogs = blogs;
    this.totalBlogs = totalBlogs;
    this.page = page;
    this.limit = limit;
  }

  private List<BlogRest> blogs;
  private long totalBlogs;
  private long page;
  private long limit;

  public List<BlogRest> getBlogs() {
    return this.blogs;
  }

  public void setBlogs(List<BlogRest> blogs) {
    this.blogs = blogs;
  }

  public long getTotalBlogs() {
    return this.totalBlogs;
  }

  public void setTotalBlogs(long totalBlogs) {
    this.totalBlogs = totalBlogs;
  }

  public long getPage() {
    return this.page;
  }

  public void setPage(long page) {
    this.page = page;
  }

  public long getLimit() {
    return this.limit;
  }

  public void setLimit(long limit) {
    this.limit = limit;
  }

}
