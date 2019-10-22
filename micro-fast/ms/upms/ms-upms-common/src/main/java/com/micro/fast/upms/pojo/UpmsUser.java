package com.micro.fast.upms.pojo;

import com.micro.fast.boot.starter.common.response.BaseConst;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UpmsUser implements Serializable {

    private Integer id;
    /**
     * 用户名必须为4-16位字母或数字的组合
     */
    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请按要求输入用户名")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{4,16}$",message = BaseConst.BASEMSG_PREFIX+"请按要求输入用户名")
    private String username;

    /**
     * 密码必须为6-16位字母或数字的组合
     */
    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请按要求输入密码")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{6,16}$",message = BaseConst.BASEMSG_PREFIX+"请按要求输入密码")
    private String password;

    private String salt;

    private String realname;

    private String avatar;

    private String phone;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请按要求输入邮箱")
    @Email(message = BaseConst.BASEMSG_PREFIX+"邮箱格式不正确")
    private String email;

    private Byte sex;

    private Byte locked;

    private Long ctime;

    private List<UpmsOrganization> organizations;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UpmsUser(Integer id, String username, String password, String salt, String realname, String avatar, String phone, String email, Byte sex, Byte locked, Long ctime,Long uptime,Integer version) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.realname = realname;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.locked = locked;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UpmsUser() {
    }
}