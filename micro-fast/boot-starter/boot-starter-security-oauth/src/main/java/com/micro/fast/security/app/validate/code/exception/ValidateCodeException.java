package com.micro.fast.security.app.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验异常,继承自AuthenticationException对象
 * @author lsy
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
