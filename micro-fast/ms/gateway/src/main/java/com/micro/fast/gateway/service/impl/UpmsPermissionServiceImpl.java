package com.micro.fast.gateway.service.impl;

import com.google.common.collect.ImmutableMap;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.feign.SystemFeign;
import com.micro.fast.gateway.feign.UpmsUserFeign;
import com.micro.fast.gateway.filter.SystemIsOpenFilter;
import com.micro.fast.gateway.pojo.dto.UpmsPermissionDTO;
import com.micro.fast.gateway.service.UpmsPermissionService;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lsy
 */
@Service
public class UpmsPermissionServiceImpl implements UpmsPermissionService {
    /**
     * 描述权限点等级 get< post < put < delete
     */
    private static ImmutableMap<String, Integer> map = ImmutableMap.of("get",0,"post",1,"put",2,"delete",3);

    @Autowired
    private UpmsUserFeign upmsUserFeign;

    @Autowired
    private SystemFeign systemFeign;

    @Override
    public AtomicBoolean checkAuthorization(String username) {
        // 1.获取请求的路由
        RequestContext currentContext = RequestContext.getCurrentContext();
        String systemRoute = (String) currentContext.get(SystemIsOpenFilter.SYSTEM_ROUTE);
        // 2. 根据请求的路由获取系统的id
        ServerResponse<Integer> systemIdByPath = systemFeign.getSystemIdByPath(systemRoute);
        if (systemIdByPath == null || !systemIdByPath.isSuccess()) {
            return new AtomicBoolean(false);
        }
        ServerResponse<HashSet<UpmsPermissionDTO>> permission = upmsUserFeign.getUserPermissionByRoleAndSystemAndType(username, systemIdByPath.getData(), 0);
        if (permission == null || !permission.isSuccess()) {
            return new AtomicBoolean(false);
        }
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        AtomicBoolean haveAuthorization = new AtomicBoolean(false);
        HashSet<UpmsPermissionDTO> data = permission.getData();
        if (data == null) {
            return new AtomicBoolean(false);
        }
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(1);
        int i = substring.indexOf("/");
        String path = substring.substring(i);
        data.forEach(item -> {
            String method = request.getMethod();
            Integer integer = map.get(method.toLowerCase());
            Integer integer1 = map.get(item.getPermissionValue().toLowerCase());
            if (antPathMatcher.match(item.getUri(), path) && (integer <= integer1)) {
                haveAuthorization.set(true);
            }
        });
        return haveAuthorization;
    }
}
