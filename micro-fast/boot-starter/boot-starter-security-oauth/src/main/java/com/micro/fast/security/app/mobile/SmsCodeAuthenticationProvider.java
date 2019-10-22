package com.micro.fast.security.app.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 进行短信登录的身份认证提供类
 * @author lsy
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 返回经过验证的Authentication
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsCodeAuthenticationToken.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户的信息");
        }
        SmsCodeAuthenticationToken resultSmsCodeAuthenticationToken = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        resultSmsCodeAuthenticationToken.setDetails(smsCodeAuthenticationToken.getDetails());
        return resultSmsCodeAuthenticationToken;
    }

    /**
     * 这个类是不是支持传入的token的校验
     * @param authenticate
     * @return
     */
    @Override
    public boolean supports(Class<?> authenticate) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authenticate);
    }
    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
