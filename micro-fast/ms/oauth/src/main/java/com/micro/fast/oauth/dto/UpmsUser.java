package com.micro.fast.oauth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lsy
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class UpmsUser implements Serializable {

    private Integer id;

    /**
     * 用户名必须为6-16位字母或数字的组合
     */
    private String username;

    /**
     * 密码必须为6-16位字母或数字的组合
     */
    private String password;

    private String salt;

    private String realname;

    private String avatar;

    private String phone;

    private String email;

    private Byte sex;

    private Byte locked;

    private Long ctime;

    private static final long serialVersionUID = 1L;
}