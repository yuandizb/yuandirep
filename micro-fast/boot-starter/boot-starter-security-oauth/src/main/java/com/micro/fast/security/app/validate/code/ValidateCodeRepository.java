package com.micro.fast.security.app.validate.code;

import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码的存储
 * @author lsy
 */
public interface ValidateCodeRepository {

    String SMS_CODE = "sms";
    String IMAGE_CODE = "image";

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code,String validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,String validateCodeType);

    /**
     * 删除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request,String validateCodeType);
}
