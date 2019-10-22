package com.micro.fast.boot.starter.common.exception.handler;

import com.micro.fast.boot.starter.common.exception.SystemException;
import com.micro.fast.boot.starter.common.exception.UserException;
import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.boot.starter.common.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类
 * @author lsy
 */
public class BaseExceptionHandlerController {
  /**
   * 处理系统异常
   * @param se
   * @return
   */
  @ExceptionHandler(SystemException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ServerResponse handlerSystemException(SystemException se) {
    String[] strings = ExceptionUtil.splitCodeMsg(se.getMessage());
    Map<String,String> map = new HashMap<>(1);
    map.put(strings[0],strings[1]);
    return ServerResponse.errorMsgData("系统异常",map);
  }

  /**
   * 处理用户异常
   * @param se
   * @return
   */
  @ExceptionHandler(UserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handlerUserException(UserException se) {
    String[] strings = ExceptionUtil.splitCodeMsg(se.getMessage());
    Map<String,String> map = new HashMap<>(1);
    map.put(strings[0],strings[1]);
    return ServerResponse.errorMsgData("处理用户异常",map);
  }

  /**
   * 用于统一处理参数校验异常
   * @param be
   * @return
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handlerBingException(BindException be){
    BindingResult bindingResult = be.getBindingResult();
    Map<String, String> validateExceptionInfo = ExceptionUtil.getValidateExceptionInfo(bindingResult);
    return ServerResponse.errorMsgData("参数校验失败",validateExceptionInfo);
  }

}
