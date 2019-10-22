package com.micro.fast.gateway.feign;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.fallback.UpmsUserFeignFallBack;
import com.micro.fast.gateway.pojo.dto.UpmsPermissionDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;

/**
 * @author lsy
 */
@FeignClient( fallback = UpmsUserFeignFallBack.class,name = "upms")
public interface UpmsUserFeign {
    /**
     * 获取用户在某一个系统内某一类型的权限
     * @param username
     * @param systemId
     * @param type
     * @return
     */
    @GetMapping("/upmsUser/role/permissions")
    ServerResponse<HashSet<UpmsPermissionDTO>> getUserPermissionByRoleAndSystemAndType(@RequestParam("username") String username,
                                                                                       @RequestParam("systemId") Integer systemId,
                                                                                       @RequestParam("type") Integer type);
}
