package com.tencent.wxcloudrun.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CardResponse {

  private String status;


  private User user;

}
