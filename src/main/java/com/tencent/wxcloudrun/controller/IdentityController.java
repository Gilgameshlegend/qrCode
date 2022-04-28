package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CardRequest;
import com.tencent.wxcloudrun.dto.CardResponse;
import com.tencent.wxcloudrun.dto.User;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.IdentityCard;
import com.tencent.wxcloudrun.service.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * counter控制器
 */
@RestController

public class IdentityController {

    final IdentityService identityService;
    final Logger logger;

    public IdentityController(@Autowired IdentityService identityService) {
        this.identityService = identityService;
        this.logger = LoggerFactory.getLogger(IdentityController.class);
    }


    /**
     * 获取当前单元通行证
     *
     * @return API response json
     */
    @GetMapping(value = "/acquire/{unitId}")
    ApiResponse acquire(@PathVariable String unitId) {
        logger.info("/acquire/{unitId} get request ,unitId={}", unitId);
        String respMsg = "success";
        CardResponse cardResponse = new CardResponse();
        try {

            IdentityCard identityCard = identityService.getUnitCardAlready(unitId);
            if (identityCard == null) {
                respMsg = "failed";
                cardResponse.setStatus(respMsg);
            } else {
                User user = new User();
                user.setAcquire_time(transform(identityCard.getStartTime()));
                user.setEnd_time(transform(identityCard.getEndTime()));
                user.setName_with_unit(identityCard.getUserName());
                user.setAvartar_url(identityCard.getUserPhoto());
            }
            return ApiResponse.ok(cardResponse);
        }catch (Exception e){
            logger.error("error in acquire : e:{}", e.getMessage());
            respMsg = "failed";
            cardResponse.setStatus(respMsg);
            return ApiResponse.ok(cardResponse);
        }
    }


    /**
     * 领取通行证
     *
     * @param request {@link CardRequest}
     * @return API response json
     */
    @PostMapping(value = "/acquire")
    ApiResponse acquire(@RequestBody CardRequest request) {
        logger.info("/acquire post request, request_unit: {}", request.getUnit_name());

        String respMsg = "success";
        CardResponse cardResponse = new CardResponse();

        try {

            IdentityCard identityCard = identityService.getUnitCardAlready(request.getUnit_name());
            if (identityCard == null) {
                String curTime = getCurrent();
                String endTime = getEndTime();
                identityService.setUnitCard(request.getUnit_name(), request.getUser().getName_with_unit(), request.getUser().getAvartar_url(), curTime, endTime);
                cardResponse.setStatus(respMsg);
                User user = new User();
                user.setAcquire_time(curTime);
                user.setEnd_time(endTime);
                user.setName_with_unit(request.getUser().getName_with_unit());
                user.setAvartar_url(request.getUser().getAvartar_url());
            } else {
                respMsg = "failed";
                cardResponse.setStatus(respMsg);

            }
            return ApiResponse.ok(cardResponse);
        } catch (Exception e) {
            logger.error("error in acquire : e:{}", e.getMessage());
            respMsg = "failed";
            cardResponse.setStatus(respMsg);
            return ApiResponse.ok(cardResponse);
        }
    }


    /**
     * 获取当前计数
     *
     * @return API response json
     */
    @GetMapping(value = "/release/{unitId}")
    ApiResponse release(@PathVariable String unitId) {
        logger.info("/release/{unitId} get request ,unitId={}", unitId);

        String respMsg = "success";
        CardResponse cardResponse = new CardResponse();
        try {
            identityService.deleteUnitCard(unitId);
            cardResponse.setStatus(respMsg);
        } catch (Exception e) {
            logger.error("error in release : e:{}", e.getMessage());
            respMsg = "failed";
            cardResponse.setStatus(respMsg);
        }
        return ApiResponse.ok(cardResponse);
    }

    public static String getCurrent() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String transform(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String getEndTime() {
        //getInstance()将返回一个Calendar的对象。
        Calendar calendar = Calendar.getInstance();
        //设置小时
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
        //格式化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(calendar.getTime());
    }

}