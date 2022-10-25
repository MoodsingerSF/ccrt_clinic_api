package com.moodsinger.ccrt_clinic.model.response;

import com.moodsinger.ccrt_clinic.io.enums.HomeCoverType;

public class HomeCoverRest {
  private String coverId;
  private HomeCoverType type;
  private String itemId;
  private String imageUrl;
  private boolean isVisible;

  public String getCoverId() {
    return coverId;
  }

  public void setCoverId(String coverId) {
    this.coverId = coverId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public HomeCoverType getType() {
    return type;
  }

  public void setType(HomeCoverType type) {
    this.type = type;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }
}
