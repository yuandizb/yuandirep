package com.micro.fast.gateway.service;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lsy
 */
public interface UpmsPermissionService {

    /**
     * 查询用户所拥有的数据权限
     * @param username 用户名
     * @return
     */
    AtomicBoolean checkAuthorization(String username);
}
