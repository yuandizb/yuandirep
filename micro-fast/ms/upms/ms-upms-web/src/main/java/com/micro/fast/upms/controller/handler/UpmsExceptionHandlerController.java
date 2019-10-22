package com.micro.fast.upms.controller.handler;

import com.micro.fast.boot.starter.common.exception.handler.BaseExceptionHandlerController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * upms异常处理基类
 * @author lsy
 */
@RestControllerAdvice(basePackages = {"com.micro.fast.upms.controller"})
public class UpmsExceptionHandlerController extends BaseExceptionHandlerController{

}
