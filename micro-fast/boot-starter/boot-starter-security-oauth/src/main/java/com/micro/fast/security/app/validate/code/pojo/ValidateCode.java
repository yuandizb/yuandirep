package com.micro.fast.security.app.validate.code.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 * @author lsy
 */
public class ValidateCode implements Serializable{

    private static final long serialVersionUID = 2406040171085392418L;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ValidateCode() {

    }
    public ValidateCode(String code, int inSeconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(inSeconds);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
