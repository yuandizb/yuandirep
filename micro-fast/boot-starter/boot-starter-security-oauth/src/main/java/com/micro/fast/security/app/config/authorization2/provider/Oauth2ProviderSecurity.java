package com.micro.fast.security.app.config.authorization2.provider;

import com.micro.fast.security.app.master.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

/**
 * oauth2服务提供商的授权拦截的配置,用于拦截/oauth/authorize过滤器链的优先级高于资源服务器授权过滤连的优先级
 * @author lsy
 */
//@EnableWebSecurity
public class Oauth2ProviderSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 安全配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http    // 表单登录的配置
                    .formLogin()
                    // 自定义的登录页面地址
                    .loginPage(securityProperties.getOauth2().getProviderLoginPage())
                    // 处理登录进程的url
                    .loginProcessingUrl(securityProperties.getOauth2().getProviderLoginProcessUrl())
                    // 登录成功处理器
                    .successHandler(successHandler)
                    // 登录失败处理器
                    .failureHandler(failureHandler)
                    .and()
                    .authorizeRequests()
                    // 任何请求都允许不进行授权
                    .anyRequest()
                    .permitAll()
                    // 符合这些规则的需要进行授权,对授权登录接口进行授权
                    .antMatchers("/oauth/authorize")
                    .authenticated()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/user/login")
                    .permitAll()
                    .and()
                    .csrf()
                    .disable();
    }
}
