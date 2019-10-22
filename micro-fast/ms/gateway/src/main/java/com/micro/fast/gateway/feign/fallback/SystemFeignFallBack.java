package com.micro.fast.gateway.feign.fallback;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.SystemFeign;
import org.springframework.stereotype.Component;

@Component
public class SystemFeignFallBack implements SystemFeign {
    @Override
    public ServerResponse getSystemStatusByPath(String path) {
        return ServerResponse.errorMsg("系统繁忙，请稍后再试");
    }

    @Override
    public ServerResponse<Integer> getSystemIdByPath(String route) {
        return ServerResponse.errorMsg("系统繁忙，请稍后再试");
    }
}
