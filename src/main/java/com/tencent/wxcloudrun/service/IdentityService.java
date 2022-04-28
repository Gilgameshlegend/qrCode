package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.IdentityCard;

import java.util.Optional;

public interface IdentityService {


  //获取本单元已经发放的证，若有则返回
  IdentityCard getUnitCardAlready(String unit);

  //发证
  void setUnitCard(String unit,String userName,String avartarUrl,String acquireTime,String endTime);

  //释放证
  void deleteUnitCard(String unit);

  void deleteOutTimeCard(String endTime);
}
