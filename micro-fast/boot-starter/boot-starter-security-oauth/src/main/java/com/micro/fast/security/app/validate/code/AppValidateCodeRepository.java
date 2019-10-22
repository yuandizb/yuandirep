package com.micro.fast.security.app.validate.code;

import com.micro.fast.security.app.validate.code.exception.ValidateCodeException;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * app模块存储验证码
 * @author lsy
 */
@Component
public class AppValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, String validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(request,validateCodeType),code,30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, String validateCodeType) {
        Object o = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        if (o==null){
            return null;
        }
        return (ValidateCode) o;
    }

    @Override
    public void remove(ServletWebRequest request, String validateCodeType) {
        redisTemplate.delete(buildKey(request,validateCodeType));
    }

    /**
     * 构建redis的key
     * @param request
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest request,String validateCodeType){
      String deviceId = request.getParameter("deviceId");

      if (StringUtils.isBlank(deviceId)){
          throw new ValidateCodeException("请求参数中缺少机器的唯一识别");
      }
      return "code:"+validateCodeType.toLowerCase()+":"+deviceId;
    }
}
