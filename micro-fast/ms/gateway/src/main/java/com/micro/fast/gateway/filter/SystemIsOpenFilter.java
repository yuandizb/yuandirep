package com.micro.fast.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.service.SystemService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断服务是否停止访问的过滤器
 * @author lsy
 */
@Component
@Slf4j
public class SystemIsOpenFilter extends ZuulFilter {

    public static final String IS_SUCCESS = "isSuccess";
    public static final String SYSTEM_ROUTE = "systemRoute";

    @Autowired
    private SystemService systemService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        // 获取当前请求对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        // 获取请求服务的路由
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");
        // 根据系统路由查找系统的开放状态
        boolean b = systemService.systemIsOpen("/" + split[1]);
        if (b){
            currentContext.setSendZuulResponse(true);
            currentContext.set(IS_SUCCESS,true);
            // 填入请求的路由
            currentContext.set(SYSTEM_ROUTE,"/" + split[1]);
        } else{
            // 终止请求
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                currentContext.setResponseBody(objectMapper.writeValueAsString(ServerResponse.errorMsg("system is closed")));
                currentContext.set(IS_SUCCESS,false);
            } catch (JsonProcessingException e) {
                log.info("对象转换成json失败");
            }
        }
        return null;
    }
}
