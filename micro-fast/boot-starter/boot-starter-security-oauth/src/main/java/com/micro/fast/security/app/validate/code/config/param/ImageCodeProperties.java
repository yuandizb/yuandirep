package com.micro.fast.security.app.validate.code.config.param;

import com.micro.fast.security.app.master.SecurityConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * 图形验证码默认的配置
 * @author lsy
 */
@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties{
    /**
     * 验证码的宽度
     */
    private int width = 67;
    /**
     * 验证码的高度
     */
    private int height = 23;

    /**
     * 覆盖短信验证码的6位长度
     */
    public ImageCodeProperties() {
        setLength(4);
        setUrl(SecurityConstants.FORM_LOGIN_PROCESSOR_URI);
    }
}
