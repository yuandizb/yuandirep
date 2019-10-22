package com.micro.fast.boot.starter.common.exception;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.util.ExceptionUtil;

/**
 * 用户异常类
 * @author lsy
 */
public class UserException extends RuntimeException{
  public UserException(BaseConst.UserResponse userResponse) {
    super(ExceptionUtil.contactCodeMsg(userResponse.getCode(),userResponse.getMsg()));
  }
}
