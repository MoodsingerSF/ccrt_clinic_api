package com.moodsinger.ccrt_clinic.model.request;

import com.moodsinger.ccrt_clinic.io.enums.HomeCoverType;

public class HomeCoverCreationRequestModel {
  private HomeCoverType type;
  private String itemId;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public HomeCoverType getType() {
    return type;
  }

  public void setType(HomeCoverType type) {
    this.type = type;
  }

}
