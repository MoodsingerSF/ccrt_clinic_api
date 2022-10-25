package com.moodsinger.ccrt_clinic.model.request;

import com.moodsinger.ccrt_clinic.io.enums.VisibilityType;

public class HomeCoverUpdateRequestModel {
  private VisibilityType visibilityType;

  public VisibilityType getVisibilityType() {
    return visibilityType;
  }

  public void setVisibilityType(VisibilityType visibilityType) {
    this.visibilityType = visibilityType;
  }
}
