package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class IdentityCard implements Serializable {

  private Integer id;

  private String unitId;

  private String userName;

  private String userPhoto;

  private Date startTime;

  private Date endTime;
}
