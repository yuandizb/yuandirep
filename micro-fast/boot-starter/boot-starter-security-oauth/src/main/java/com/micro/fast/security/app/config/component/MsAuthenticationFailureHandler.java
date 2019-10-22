package com.micro.fast.security.app.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.fast.security.app.master.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationFailureHandler
 * 身份认证失败的后续处理类
 * @author lsy
 */
@Component("msAuthenticationFailureHandler")
public class MsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(MsAuthenticationFailureHandler.class);
    /**
     * object <=> json 处理对象
     */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 安全框架配置参数对象
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , AuthenticationException e)
            throws IOException, ServletException {
            //根据配置参数判断身份认证失败后的返回类型进行响应
            log.info("failure");
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e));
    }
}
