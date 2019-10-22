package com.micro.fast.boot.starter.common.exception;


import com.micro.fast.boot.starter.common.response.BaseConst;
import com.micro.fast.boot.starter.common.util.ExceptionUtil;

/**
 * 系统级别的异常,比如数据哭插入失败
 * @author lsy
 */
public class SystemException extends RuntimeException {
  public SystemException(BaseConst.SystemResponse systemResponse) {
    super(ExceptionUtil.contactCodeMsg(systemResponse.getCode(),systemResponse.getMsg()));
  }
}
