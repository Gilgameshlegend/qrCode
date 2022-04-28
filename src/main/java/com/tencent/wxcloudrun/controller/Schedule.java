package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.service.IdentityService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class Schedule {

    @Autowired
    private IdentityService identityService;

    public static ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("refresh-pool-%d").daemon(true).build());

    public void init() {
        pool.scheduleWithFixedDelay(() -> {
                identityService.deleteOutTimeCard(getCurrent());
        },0,3, TimeUnit.SECONDS);
    }


    public static String getCurrent() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
