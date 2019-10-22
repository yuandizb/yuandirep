package com.micro.fast.security.app.validate.code.processor.impl;

import com.micro.fast.security.app.validate.code.ValidateCodeRepository;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import com.micro.fast.security.app.validate.code.processor.ValidateCodeProcessor;
import com.micro.fast.security.app.validate.code.util.ValidateCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * 验证码controller中执行操作的抽象方法
 * @author lsy
 */
public abstract class AbstractValidateCodeProcessor <T extends ValidateCode> implements ValidateCodeProcessor {




  /**
   * 收集系统中所有{@link ValidateCodeUtil}的实现,并将bean的名字作为key
   */
  @Autowired
  private Map<String,ValidateCodeUtil> validateCodeUtils;

  private static final String VALIDATE_CODE_UTIL_SUFFIX = "CodeUtil";

  @Autowired
  private ValidateCodeRepository validateCodeRepository;

  @Override
  public void create(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, IOException {
    T generateCode = generateCode(servletWebRequest);
    save(servletWebRequest,generateCode);
    send(servletWebRequest,generateCode);
  }

  /**
   * 根据请求的类型生成验证码
   * @param servletWebRequest
   * @return
   */
  @SuppressWarnings("unchecked")
  public T generateCode(ServletWebRequest servletWebRequest){
    String processorType = getProcessorType(servletWebRequest);
    //根据请求的类型获取指定的验证码生成器
    ValidateCodeUtil validateCodeUtil = validateCodeUtils.get(processorType + VALIDATE_CODE_UTIL_SUFFIX);
    return (T) validateCodeUtil.createValidateCode(servletWebRequest);
  }

  /**
   * 保存验证码
   * @param servletWebRequest
   * @param validateCode 生成的验证码
   */
  public void save(ServletWebRequest servletWebRequest,T validateCode){
    //转化成一般类型的验证码
    ValidateCode saveValidateCode = new ValidateCode();
    saveValidateCode.setCode(validateCode.getCode());
    saveValidateCode.setExpireTime(validateCode.getExpireTime());
    validateCodeRepository.save(servletWebRequest,saveValidateCode,getProcessorType(servletWebRequest));
  }

  /**
   * 发送验证码的逻辑，延迟到子类中去实现
   * @param servletWebRequest 请求
   * @param validateCode 验证码
   * @throws ServletRequestBindingException 请求中不包含phone对象时抛出异常
   * @throws IOException
   */
  public abstract void send(ServletWebRequest servletWebRequest,T validateCode) throws ServletRequestBindingException, IOException;

  /**
   * 根据请求的url获取请求验证码的类型
   * @param servletWebRequest
   * @return type 获取请求验证码的类型
   */
  private String getProcessorType(ServletWebRequest servletWebRequest){
    return StringUtils.substringAfter(servletWebRequest.getRequest().getRequestURI(),"/code/");
  }

}
