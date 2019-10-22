package com.micro.fast.security.app.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.fast.security.app.master.SecurityProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证成功后的处理类,在登录成功后进行授权token的处理
 *
 * @author lsy
 */
@Component("msAuthenticationSuccessHandler")
public class MsAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(MsAuthenticationSuccessHandler.class);

    /**
     * 安全框架配置参数对象
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * object <=> json 处理对象
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("defaultAuthorizationServerTokenServices")
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    /**
     * 登录成功后的处理方法
     *
     * @param request
     * @param response
     * @param authentication      用户认证的详细信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication
    ) throws IOException, ServletException {
        log.info("登录成功，开始授权令牌");
        //1.校验clientId和clientSecret是否对应
        String header = request.getHeader("Authorization");
        if (header==null){
            throw new UnapprovedClientAuthenticationException("请求头中client信息不存在");
        }
        if (!header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中client信息不存在");
        }
        String[] tokens = this.extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:"+clientId);
        }else if (!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:"+clientSecret);
        }
        //2.自定义流程创建access_Token
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(auth2Authentication);
        //3.返回accessToken
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));

    }

    /**
     * 对header进行解码
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, this.getCredentialsCharset(request));
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return "UTF-8";
    }
}
