package com.moodsinger.ccrt_clinic.shared.dto;

import com.moodsinger.ccrt_clinic.io.enums.HomeCoverType;
import com.moodsinger.ccrt_clinic.io.enums.VisibilityType;

public class HomeCoverDto {
  private String coverId;
  private HomeCoverType type;
  private String itemId;
  private int serialNo = 0;
  private String imageUrl;
  private boolean isVisible;
  private VisibilityType visibilityType;

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

  public int getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(int serialNo) {
    this.serialNo = serialNo;
  }

  public HomeCoverType getType() {
    return type;
  }

  public void setType(HomeCoverType type) {
    this.type = type;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  public VisibilityType getVisibilityType() {
    return visibilityType;
  }

  public void setVisibilityType(VisibilityType visibilityType) {
    this.visibilityType = visibilityType;
  }

}
