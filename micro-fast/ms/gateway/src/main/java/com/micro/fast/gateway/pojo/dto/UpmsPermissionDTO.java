package com.micro.fast.gateway.pojo.dto;

import com.micro.fast.boot.starter.common.response.BaseConst;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * upms后台权限传输类
 * @author lsy
 */
@Data
public class UpmsPermissionDTO implements Serializable {
    private Integer id;

    private Integer systemId;

    private Integer pid;

    private String name;

    private String description;

    /**
     * 0 表示数据权限 1 表示视图权限
     */
    private Byte type;

    private String permissionValue;

    private String uri;

    private String icon;

    private Byte status;

    private Long orders;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;
}
