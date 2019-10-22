package com.micro.fast.security.app.validate.code.util;

import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成的接口
 * @author Administrator
 */
public interface ValidateCodeUtil {
    /**
     * 生成验证码
     * @param request 封装request和response的对象
     * @return 验证码对象
     */
    ValidateCode createValidateCode(ServletWebRequest request);
}
