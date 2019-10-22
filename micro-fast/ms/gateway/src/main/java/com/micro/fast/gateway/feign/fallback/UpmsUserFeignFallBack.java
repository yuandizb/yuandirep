package com.micro.fast.gateway.feign.fallback;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.UpmsUserFeign;
import com.micro.fast.gateway.pojo.dto.UpmsPermissionDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author lsy
 */
@Component
public class UpmsUserFeignFallBack implements UpmsUserFeign {
    @Override
    public ServerResponse<HashSet<UpmsPermissionDTO>> getUserPermissionByRoleAndSystemAndType(String username, Integer systemId, Integer type) {
        return ServerResponse.errorMsg("系统繁忙，请稍后再试");
    }
}
