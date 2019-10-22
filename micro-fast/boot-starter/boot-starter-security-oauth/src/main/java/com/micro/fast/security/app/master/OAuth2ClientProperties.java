package com.micro.fast.security.app.master;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lsy
 */
@Getter
@Setter
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds = 2592000;

    private String[] authorizedGrantTypes = new String[]{"refresh_token","password","custom"};

    private String[] scopes = new String[]{"all","read","write"};
}
