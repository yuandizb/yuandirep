package com.micro.fast.security.app.validate.code.config.param;

import com.micro.fast.security.app.master.SecurityConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lsy
 */
@Getter
@Setter
public class SmsCodeProperties {

  /**
   * 验证码数字的长度
   */
  private int length = 6;
  /**
   * 验证码的有效时间
   */
  private int expireIn = 60;
  /**
   * 需要进行验证码校验的url
   */
  private String url = SecurityConstants.MOBILE_LOGIN_PROCESSOR_URI;
}
