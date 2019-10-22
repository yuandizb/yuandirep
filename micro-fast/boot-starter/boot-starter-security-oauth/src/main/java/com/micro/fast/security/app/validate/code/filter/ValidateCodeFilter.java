package com.micro.fast.security.app.validate.code.filter;

import com.micro.fast.security.app.master.SecurityConstants;
import com.micro.fast.security.app.master.SecurityProperties;
import com.micro.fast.security.app.validate.code.ValidateCodeRepository;
import com.micro.fast.security.app.validate.code.exception.ValidateCodeException;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 图片验证码校验过滤器
 *
 * @author LSY
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

  public static final String IMAGE = "IMAGE";
  public static final String SMS = "SMS";


  private AuthenticationFailureHandler authenticationFailureHandler;

  private ValidateCodeRepository validateCodeRepository;




  private SecurityProperties securityProperties;

  /**
   * 需要进行验证码校验的路径和校验的类型的map
   */
  private Map<String, String> filterUrlMap = new HashMap<>();

  private static final String REQUEST_METHOD = "post";

  /**
   * 初始化filterUrlMap
   *
   * @throws ServletException
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    String imageUrl = securityProperties.getCode().getImage().getUrl();
    putValidateUrlsToMap(imageUrl, IMAGE);
    String smsUrl = securityProperties.getCode().getSms().getUrl();
    putValidateUrlsToMap(smsUrl, SMS);
  }

  /**
   * 将需要校验的请求路径和校验类类型存入filterUrlMap中
   *
   * @param patterns
   * @param type
   */
  private void putValidateUrlsToMap(String patterns, String type) {
    String[] urls = patterns.split(",");
    for (String url : urls) {
      filterUrlMap.put(url, type);
    }
    if (IMAGE.equals(type)) {
      filterUrlMap.put(SecurityConstants.FORM_LOGIN_PROCESSOR_URI, type);
    }
    if (SMS.equals(type)) {
      filterUrlMap.put(SecurityConstants.MOBILE_LOGIN_PROCESSOR_URI, type);
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest
      , HttpServletResponse httpServletResponse
      , FilterChain filterChain)
      throws ServletException, IOException {
    //判断是否需要验证码校验
    boolean filter = false;
    //进行校验的类型
    String validateType = "";
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    Set<String> filterUrls = filterUrlMap.keySet();
    Iterator<String> iterator = filterUrls.iterator();
    while (iterator.hasNext()) {
      String next = iterator.next();
      if (antPathMatcher.match(next, httpServletRequest.getRequestURI())) {
        filter = true;
        validateType = filterUrlMap.get(next);
        break;
      }
    }
    //在登录post请求的时候对验证码进行校验
    if (filter && StringUtils.endsWithIgnoreCase(httpServletRequest.getMethod(), REQUEST_METHOD)) {
      try {
        //校验验证码是否正确
        validate(new ServletWebRequest(httpServletRequest),validateType);
      } catch (ValidateCodeException e) {
        //进行身份认证失败后的处理
        authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        //身份认证失败的时候直接return返回给页面信息，不进行后续的
        return;
      }
    }
    //执行后续的过滤器
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  /**
   * 验证码校验
   *
   * @param servletWebRequest
   * @param validateType
   */
  private void validate(ServletWebRequest servletWebRequest, String validateType) {
//
    //获取存储的验证码
    ValidateCode validateCode = validateCodeRepository.get(servletWebRequest, validateType.toLowerCase());

    String code = servletWebRequest.getParameter(validateType.toLowerCase()+"Code");

    //session和用户传入的验证码不能为空
    if (validateCode == null || StringUtils.isBlank(code)) {
      throw new ValidateCodeException("未生成验证码对象或用户传入的验证码为空");
    }
    if (validateCode.getCode() == null
        || !validateCode.getCode().equals(code)) {
      throw new ValidateCodeException("验证码校验失败");
    }
    if (validateCode.isExpire()) {
      throw new ValidateCodeException("验证码已过期");
    }
    //如果校验通过之后删除验证码
    validateCodeRepository.remove(servletWebRequest,validateType.toLowerCase());
  }

  public AuthenticationFailureHandler getAuthenticationFailureHandler() {
    return authenticationFailureHandler;
  }

  public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  public Map<String, String> getFilterUrlMap() {
    return filterUrlMap;
  }

  public void setFilterUrlMap(Map<String, String> filterUrlMap) {
    this.filterUrlMap = filterUrlMap;
  }

  public SecurityProperties getSecurityProperties() {
    return securityProperties;
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
  public ValidateCodeRepository getValidateCodeRepository() {
    return validateCodeRepository;
  }

  public void setValidateCodeRepository(ValidateCodeRepository validateCodeRepository) {
    this.validateCodeRepository = validateCodeRepository;
  }
}
