package com.micro.fast.security.app.config.authorization2;

import com.micro.fast.security.app.config.component.MsAuthenticationFailureHandler;
import com.micro.fast.security.app.config.component.MsAuthenticationSuccessHandler;
import com.micro.fast.security.app.master.SecurityConstants;
import com.micro.fast.security.app.master.SecurityProperties;
import com.micro.fast.security.app.mobile.SmsCodeAuthenticationSecurityConfig;
import com.micro.fast.security.app.validate.code.ValidateCodeRepository;
import com.micro.fast.security.app.validate.code.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * OAuth2协议标准服务提供实现配置类，资源服务器的配置，其中服用了oauth服务商签发令牌的流程。
 * 用于受信任的自家的应用进行多种模式(图片验证码，手机短信验证，社交登录)的登录。
 * 这个过滤器链的优先级低于oauth2服务商中httpSecurity的优先级
 * @author lsy
 */
@Configuration
@EnableResourceServer
public class AuthorizationResourceConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private MsAuthenticationFailureHandler msAuthenticationFailureHandler;

    @Autowired
    private MsAuthenticationSuccessHandler msAuthenticationSuccessHandler;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 短信验证码登录的配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //创建自定义的验证码过滤器，填入身份认证失败处理对象
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(msAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.setValidateCodeRepository(validateCodeRepository);
        validateCodeFilter.afterPropertiesSet();

        http
                //添加自定义的验证码处理器，在用户密码校验过滤器的方式获取令牌之前
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //使用表单登录
                .formLogin()
                //自定义登录页面
                .loginPage(SecurityConstants.LOGIN_PAGE)
                //配置spring security登录接口的访问地址,用于在自定义的登录页面提交请求
                .loginProcessingUrl(SecurityConstants.FORM_LOGIN_PROCESSOR_URI)
                //定义身份认证成功后处理对象
                .successHandler(msAuthenticationSuccessHandler)
                //定义身份认证失败后处理对象
                .failureHandler(msAuthenticationFailureHandler)
                .and()
                //下面是权限配置
                .authorizeRequests()
                //当访问这个页面的时候不进行身份认证,对用户自定义的登录页也不进行权限认证
                .antMatchers(SecurityConstants.MOBILE_LOGIN_PROCESSOR_URI
                        , SecurityConstants.FORM_LOGIN_PROCESSOR_URI
                        , SecurityConstants.GET_VALIDATE_CODE_URI
                        , SecurityConstants.LOGIN_PAGE
                        // 开发对数据库连接池的访问权限
                        , "/druid/**"
                        // 开发oauth提供商的权限
                        , "/oauth/**"
                )
                .permitAll()
                //任何请求
                .anyRequest()
                //都需要身份认证
                .authenticated()
                .and()
                //暂时关闭掉跨站请求攻击防护
                .csrf().disable()
                //应用短信验证码登录的逻辑
                .apply(smsCodeAuthenticationSecurityConfig)
                .and();
    }
}
