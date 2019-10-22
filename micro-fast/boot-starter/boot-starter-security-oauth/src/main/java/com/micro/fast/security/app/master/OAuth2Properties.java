package com.micro.fast.security.app.master;

import lombok.Getter;
import lombok.Setter;

/**
 * aouth2属性配置
 * @author lsy
 */
@Getter
@Setter
public class OAuth2Properties {

    /**
     * oauth2客户端详情
     */
    private OAuth2ClientProperties[] clients = new OAuth2ClientProperties[]{};

    /**
     * jwt签名所需要的秘钥
     */
    private String jwtSigningKey = "msjwt";

    /**
     * 登录认证token的方式
     */
    private String storeType = "jwt";

    /**
     * 服务提供商授权时用户的登录界面
     */
    private String providerLoginPage = "/oauth/login";

    /**
     * 服务提供商授权时处理登录的路径
     */
    private String providerLoginProcessUrl = "/oauth/login/process";

    private String clientStoreType = "mysql";
}
