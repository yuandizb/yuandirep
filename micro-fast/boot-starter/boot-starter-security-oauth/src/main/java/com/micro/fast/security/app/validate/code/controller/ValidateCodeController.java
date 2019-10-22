package com.micro.fast.security.app.validate.code.controller;

import com.micro.fast.security.app.master.SecurityConstants;
import com.micro.fast.security.app.validate.code.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 图片验证码控制层
 * @author lsy
 */
@RestController
public class ValidateCodeController {

    /**
     * Collect all objects that implement {@link ValidateCodeProcessor} interface
     * 收集所有实现了{@link ValidateCodeProcessor} 接口的类
     */
    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;

    private static final String VALIDATE_CODE_PROCESSOR_SUFFIX = "ValidateCodeProcessor";

    @GetMapping(SecurityConstants.GET_VALIDATE_CODE_PREFIX+"{type}")
    public void createCode(HttpServletResponse response, HttpServletRequest request,@PathVariable("type") String type) throws IOException, ServletRequestBindingException {
        //create save send ValidateCode
        validateCodeProcessors.get(type+VALIDATE_CODE_PROCESSOR_SUFFIX).create(new ServletWebRequest(request,response));
    }

}
