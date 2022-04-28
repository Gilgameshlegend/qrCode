package com.tencent.wxcloudrun;

import com.tencent.wxcloudrun.controller.Schedule;
import com.tencent.wxcloudrun.util.ContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = {"com.tencent.wxcloudrun.dao"})
public class WxCloudRunApplication {

  private static ApplicationContext applicationContext;


  public static void main(String[] args) {
    SpringApplication.run(WxCloudRunApplication.class, args);
    ContextUtil.getApplicationContext().getBean(Schedule.class).init();
  }
}
