package com.micro.fast.security.app.master;

/**
 * 身份认证框架常亮类
 * @author lsy
 */
public interface SecurityConstants {

  /**
   * 触发身份认证请求的时候跳转到的uri
   */
  String LOGIN_PAGE = "/authentication/require";

  /**
   * 处理表单登录请求的uri
   */
  String FORM_LOGIN_PROCESSOR_URI = "/user/login";

  /**
   * 处理手机短信登录的uri
   */
  String MOBILE_LOGIN_PROCESSOR_URI = "/authentication/mobile";

  /**
   * 手机短信身份认证时从请求中获取手机号的key
   */
  String MOBILE_LOGIN_PROCESSOR_PARAM_MOBILE_KEY = "mobile";

  /**
   * 获取验证码的uri
   */
  String GET_VALIDATE_CODE_URI = SecurityConstants.GET_VALIDATE_CODE_PREFIX+"*";

  /**
   * 获取验证码请求的前缀
   */
  String GET_VALIDATE_CODE_PREFIX = "/code/";
}
