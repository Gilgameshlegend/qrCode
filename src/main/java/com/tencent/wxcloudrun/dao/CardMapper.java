package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.IdentityCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CardMapper {

    void setUnitCard(@Param("unit_id") String unitId, @Param("user_name") String userName,
                             @Param("user_photo") String userPhoto, @Param("start_time") String startTime,
                             @Param("end_time") String endTime);


    IdentityCard getUnitCardAlready(@Param("unit_id") String unitId);

    void deleteUnitCardAlready(@Param("unit_id") String unitId);

}
