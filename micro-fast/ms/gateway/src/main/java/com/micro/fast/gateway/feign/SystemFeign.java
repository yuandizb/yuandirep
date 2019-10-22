package com.micro.fast.gateway.feign;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.fallback.SystemFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <pre>
 * 远程获取系统信息的类
 * upms 权限管理系统
 * </pre>
 * @author lsy
 */
@FeignClient(fallback = SystemFeignFallBack.class,name = "upms")
public interface SystemFeign {

    /**
     * 根据系统的路由查询系统的状态
     * @param route 用户的访问路由
     * @return
     */
    @GetMapping("/upmsSystem/getSystemStatusByRoute")
    ServerResponse<Integer> getSystemStatusByPath(@RequestParam("route") String route);

    /**
     * 根据系统的路由查询系统的id
     * @param route 系统路由
     * @return
     */
    @GetMapping("/upmsSystem/getSystemIdByRoute")
    ServerResponse<Integer> getSystemIdByPath(@RequestParam("route") String route);


}
