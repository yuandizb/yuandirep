package com.micro.fast.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.gateway.config.Config;
import com.micro.fast.gateway.jwt.ValidateJwtToken;
import com.micro.fast.gateway.service.UpmsPermissionService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 对accessToken进行校验
 * @author lsy
 */
@Component
@Slf4j
public class AuthorizationRequestFilter extends ZuulFilter {

    public static final String USER_NAME = "user_name";

    @Value("${jwt.signingKey:msjwt}")
    private String signingKey;

    @Autowired
    private Config config;

    @Autowired
    private UpmsPermissionService upmsPermissionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }



    @Override
    public Object run() {
        //校验登录状态和访问权限
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 如果上一个过滤器终止请求,则终止请求
        if (requestContext.get(SystemIsOpenFilter.IS_SUCCESS).equals(false)){
            return null;
        }
        HttpServletRequest request = requestContext.getRequest();
        ServerResponse validate = ValidateJwtToken.validate(request, signingKey);
        ArrayList<String> notNeedLoginCheckPath = config.getNotNeedLoginCheckPath();
        if (notNeedLoginCheckPath!=null && notNeedLoginCheckPath.size() > 0){
            AtomicBoolean notNeedLogin = new AtomicBoolean(false);
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            notNeedLoginCheckPath.forEach(item -> {
                if (antPathMatcher.match(item, request.getRequestURI())) {
                    notNeedLogin.set(true);
                }
            });
            if (notNeedLogin.get()) {
                requestContext.setSendZuulResponse(true);
                return null;
            }
        }
        // 用户已经登录
        if (validate.isSuccess()) {
            Claims data = (Claims)validate.getData();
            AtomicBoolean atomicBoolean = upmsPermissionService.checkAuthorization((String) data.get(USER_NAME));
            boolean havePermission = atomicBoolean.get();
            if (havePermission) {
                // 对请求进行后续处理
                requestContext.setSendZuulResponse(true);
            } else {
                requestContext.setSendZuulResponse(false);
                try {
                    HashMap<Integer, String> map = Maps.newHashMap();
                    map.put(BaseConst.UserResponse.UN_HAVE_PERMISSION.getCode(),BaseConst.UserResponse.UN_HAVE_PERMISSION.getMsg());
                    requestContext.setResponseBody(objectMapper.writeValueAsString(ServerResponse.errorMsgData("无权限操作",map)));
                    requestContext.getResponse().setCharacterEncoding("utf-8");
                } catch (JsonProcessingException e) {
                    log.info("{}",e);
                }
            }
        }
        return null;
    }
}
