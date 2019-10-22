package com.micro.fast.security.app.validate.code.config;

import com.micro.fast.security.app.master.SecurityProperties;
import com.micro.fast.security.app.validate.code.service.SendSmsCode;
import com.micro.fast.security.app.validate.code.service.impl.DefaultSendSmsCodeImpl;
import com.micro.fast.security.app.validate.code.util.ImageCodeUtil;
import com.micro.fast.security.app.validate.code.util.SmsCodeUtil;
import com.micro.fast.security.app.validate.code.util.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码实现类的配置
 * @author lsy
 */
@Configuration
public class ValidateCodeBean {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 增量开发，在保持原有代码逻辑不变的基础之上添加新的方法进行替换
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeUtil")
    public ValidateCodeUtil imageCodeUtil(){
        ImageCodeUtil imageCodeUtil = new ImageCodeUtil();
        imageCodeUtil.setSecurityProperties(securityProperties);
        return  imageCodeUtil;
    }

    /**
     * 提供默认实现的短信发送
     */
    @Bean
    @ConditionalOnMissingBean(name = "sendSmsCode")
    public SendSmsCode sendSmsCode(){
        DefaultSendSmsCodeImpl defaultSendSmsCode = new DefaultSendSmsCodeImpl();
        return defaultSendSmsCode;
    }

    /**
     * 提供默认的短信验证码生成实现
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeUtil")
    public ValidateCodeUtil smsCodeUtil(){
        SmsCodeUtil smsCodeUtil = new SmsCodeUtil();
        smsCodeUtil.setSecurityProperties(securityProperties);
        return smsCodeUtil;
    }


}
