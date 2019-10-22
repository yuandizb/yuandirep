package com.micro.fast.oauth.feign;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.oauth.dto.UpmsUser;
import com.micro.fast.oauth.feign.fallback.UpmsUserServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程获取用户信息
 * @author lsy
 */
@FeignClient(fallback = UpmsUserServiceFallback.class,name= "upms")
public interface UpmsUserService {

    /**
     * 根据用户名获取用户的信息
     * @param username 用户名
     * @return
     */
    @GetMapping("/upmsUser/username/{username}")
    ServerResponse<UpmsUser> getUserByUserName(@PathVariable(name = "username") String username);
}
