package com.micro.fast.gateway.jwt;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

/**
 * 校验后传入的jwtToken
 * @author lsy
 */
public class ValidateJwtToken {
    /**
     * 校验jwtToken
     */
    public static ServerResponse<Claims> validate(HttpServletRequest request,String signingKey){
        String authorization = request.getHeader("Authorization");
        if (authorization==null){
            return ServerResponse.error();
        }
        String token = StringUtils.substringAfter(authorization, "bearer ");
        if (StringUtils.isBlank(token)){
            return ServerResponse.error();
        }
        //解析jwt
        Claims body = null;
        try {
            body = Jwts.parser().setSigningKey(signingKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (UnsupportedEncodingException e) {
            return ServerResponse.error();
        }
        return ServerResponse.successData(body);
    }
}
