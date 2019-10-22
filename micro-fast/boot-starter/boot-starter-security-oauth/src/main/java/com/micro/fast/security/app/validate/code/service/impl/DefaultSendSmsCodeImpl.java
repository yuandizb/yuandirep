package com.micro.fast.security.app.validate.code.service.impl;

import com.micro.fast.security.app.validate.code.service.SendSmsCode;

/**
 * @author lsy
 */
public class DefaultSendSmsCodeImpl implements SendSmsCode {

  @Override
  public void sendCode(String phone, String code) {
    System.out.println("向"+phone+"发送"+code);
  }
}
