package com.micro.fast.security.app.validate.code.service;

/**
 * @author lsy
 */
public interface SendSmsCode {
  /**
   * 发送短信验证码
   * @param phone
   * @param code
   */
  void sendCode(String phone,String code);
}
