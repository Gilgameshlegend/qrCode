package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CardMapper;
import com.tencent.wxcloudrun.model.IdentityCard;
import com.tencent.wxcloudrun.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {

    final CardMapper ca;

    public IdentityServiceImpl(@Autowired CardMapper ca) {
        this.ca = ca;
    }


    @Override
    public IdentityCard getUnitCardAlready(String unit) {
        return ca.getUnitCardAlready(unit);
    }

    @Override
    public void setUnitCard(String unit, String userName, String avartarUrl, String acquireTime, String endTime) {
        ca.setUnitCard(unit, userName, avartarUrl, acquireTime, endTime);
    }

    @Override
    public void deleteUnitCard(String unit) {
        ca.deleteUnitCardAlready(unit);
    }

    @Override
    public void deleteOutTimeCard(String endTime) {
        ca.deleteOutTimeCard(endTime);
    }
}
