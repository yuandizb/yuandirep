package com.micro.fast.security.app.controller;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.security.app.master.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 触发身份认证的请求
 * @author lsy
 */
@Slf4j
@RestController
public class ResourceSecurityController {

    /**
     * 用于获取引发身份认证的请求缓存
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 告知前台，需要进行登录。
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(SecurityConstants.LOGIN_PAGE)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ServerResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据当前请求获取上一次请求的缓存
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = null;
        if(savedRequest !=null){
            redirectUrl= savedRequest.getRedirectUrl();
        }
        if (redirectUrl!=null){
            log.info("用户访问的url是{}但未进行身份认证，触发了跳转。",redirectUrl);
        }
        return ServerResponse.errorMsg("未进行身份认证");
    }
}
