package com.micro.fast.security.app.validate.code.config.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置参数
 * @author lsy
 */
@Getter
@Setter
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
