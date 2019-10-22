package com.micro.fast.gateway.service.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.SystemFeign;
import com.micro.fast.gateway.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SystemServiceImpl implements SystemService{

    public static final String GATEWAY_SYSTEM_STATUS = "gateway:systemStatus:";
    public static final int OPEN = 1;

    @Autowired
    private SystemFeign systemFeign;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean systemIsOpen(String path) {
        // 尝试从缓存中获取系统的状态
        String status = redisTemplate.opsForValue().get(GATEWAY_SYSTEM_STATUS + path);
        if (StringUtils.isBlank(status)){
            // 如果没有缓存,尝试从远程获取
            ServerResponse<Integer> systemStatusByPath = systemFeign.getSystemStatusByPath(path);
            if (systemStatusByPath.isSuccess()){
                // 获取成功就进行缓存
                Integer data = systemStatusByPath.getData();
                redisTemplate.opsForValue().set(GATEWAY_SYSTEM_STATUS+path,String.valueOf(data),60, TimeUnit.SECONDS);
                return OPEN == data;
            } else {
                return false;
            }
        } else {
            return Integer.valueOf(status) == OPEN;
        }

    }
}
