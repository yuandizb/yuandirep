package com.micro.fast.boot.starter.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * 对异常处理的工具类
 * @author lsy
 */
public class ExceptionUtil {
  /**
   * 分隔符
   */
  public static final String split = "::::";

  /**
   * 合并异常代码和描述
   * @param code
   * @param msg
   * @return
   */
  public static String contactCodeMsg(Integer code,String msg){
    return String.valueOf(code)+ExceptionUtil.split+msg;
  }

  /**
   * 拆分异常描述
   * @return
   */
  public static String[] splitCodeMsg(String codeMsg){
    String[] split = codeMsg.split(ExceptionUtil.split);
    return split;
  }

  /**
   * 参数绑定异常进行处理
   */
  public static Map<String,String> getValidateExceptionInfo(BindingResult errors){
    Map<String,String> map = new HashMap<>(5);
    if (errors.hasErrors()) {
      errors.getAllErrors().stream().forEach(objectError -> {
        FieldError fieldError = (FieldError) objectError;
        // 接收参数绑定异常信息
        String defaultMessage = fieldError.getDefaultMessage();
        // 对信息进行拆分放入map
        String[] strings = splitCodeMsg(defaultMessage);
        map.put(strings[0],strings[1]);
      });
    }
    return map;
  }
}
