package com.micro.fast.security.app.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author lsy
 * 短信验证码登录的配置类
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{

  @Autowired
  @Qualifier("msAuthenticationSuccessHandler")
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  @Qualifier("msAuthenticationFailureHandler")
  private AuthenticationFailureHandler authenticationFailureHandler;

  /**
   *   todo 需要改成根据手机号获取用户的信息
   */
  @Autowired
  @Qualifier("msUserDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {

    SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
    //将短信验证码登录过滤器放入AuthenticationManager,方便后续调用
    smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
    smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

    //将根据手机号获取用户信息的对象放入短信验证码的provider中
    SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
    smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
    //将自定义的provider放入authenticaionManger管理的集合中去
    http.authenticationProvider(smsCodeAuthenticationProvider)
        //将自定义的过滤器添加到用户账号密码校验过滤器后面
        .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
