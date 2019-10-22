package com.micro.fast.security.app.validate.code.processor;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码校验controller中需要执行操作的抽象接口
 * @author lsy
 */
public interface ValidateCodeProcessor {

  /**
   * 验证码存储到session中的前缀
   */
   String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";


  /**
   * 创建验证码，将验证码写入session中，输出验证码
   * @param servletWebRequest　封装请求和响应
   * @throws IOException
   * @throws ServletRequestBindingException
   */
  void create(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, IOException;
  
}
