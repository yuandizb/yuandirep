package com.micro.fast.security.app.validate.code.util;

import com.micro.fast.security.app.master.SecurityProperties;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lsy
 */
public class SmsCodeUtil implements ValidateCodeUtil {

  private SecurityProperties securityProperties;

  @Override
  public ValidateCode createValidateCode(ServletWebRequest request) {
    //生成指定位数的验证码
    String random = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
    ValidateCode validateCode;
    validateCode = new ValidateCode(random, securityProperties.getCode().getSms().getExpireIn());
    return validateCode;
  }

  public SecurityProperties getSecurityProperties() {
    return securityProperties;
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
