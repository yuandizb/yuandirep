package com.micro.fast.security.app.validate.code.processor.impl;

import com.micro.fast.security.app.master.SecurityConstants;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import com.micro.fast.security.app.validate.code.service.SendSmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lsy
 */
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor{

    @Autowired
    private SendSmsCode sendSmsCode;

    @Override
    public void send(ServletWebRequest servletWebRequest, ValidateCode validateCode) throws ServletRequestBindingException {
        //获取phone,请求中必须包含用户的手机号
        String phone = ServletRequestUtils.getRequiredStringParameter(servletWebRequest.getRequest(), SecurityConstants.MOBILE_LOGIN_PROCESSOR_PARAM_MOBILE_KEY);
        //发送验证码的逻辑
        sendSmsCode.sendCode(phone,validateCode.getCode());
    }
}
