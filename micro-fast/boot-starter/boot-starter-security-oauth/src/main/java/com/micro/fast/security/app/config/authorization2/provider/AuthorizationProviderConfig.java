package com.micro.fast.security.app.config.authorization2.provider;

import com.micro.fast.security.app.master.OAuth2ClientProperties;
import com.micro.fast.security.app.master.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 *  /oauth/authorize 第三方点击进行开始授权的url。这个地址应该是受spring-security保护的，只有登录的用户才能访问
 *  /oauth/confirm_access 确认授权
 *  /oauth/token  第三方获取token的地址
 *  /oauth/error 授权发生错误时默认的处理地址
 *  /oauth/check_token 解码校验token的地址
 *  /oauth/token_key 使用jwtToken时暴露publicKey的地址
 * OAuth2协议标准服务商实现配置类,配置token的生成存储策略。用于第三方的应用进行登录
 * @author lsy
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationProviderConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    /**
     * jwtToken转换器
     * 只有在jwt环境下才生效
     */
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 配置token的形式，存取策略,以及增强策略
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if (jwtAccessTokenConverter!=null && jwtTokenEnhancer!=null){
            //声明token增强链
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            //将增强逻辑填入增强链
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            //将增强链放入扩展点中
            endpoints.tokenEnhancer(enhancerChain)
                    // 用于解析jwtToken
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 配置哪些应用可以获取token
     * 可以配置在内存中存贮，也可以配置在数据库中存储.
     * 存储在数据中的时候可以使用 JdbcClientDetailsService对信息进行动态修改
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        if ("mysql".equals(securityProperties.getOauth2().getClientStoreType())) {
            JdbcClientDetailsServiceBuilder jdbc = clients.jdbc(dataSource);
        } else {
            InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
            if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
                for (OAuth2ClientProperties client: securityProperties.getOauth2().getClients()){
                    builder.withClient(client.getClientId()).secret(client.getClientSecret())
                            //token的过期时间
                            .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                            //授权类型
                            .authorizedGrantTypes(client.getAuthorizedGrantTypes())
                            //refreshToken的过期时间，refreshToken是用来在用户无感知的情况下换取token的
                            .refreshTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                            //授权scope
                            .scopes(client.getScopes());
                }
            }
        }
    }
}
