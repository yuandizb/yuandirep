package com.micro.fast.upms.pojo;

import com.micro.fast.boot.starter.common.response.BaseConst;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpmsUserOrganization implements Serializable {
    private Integer id;

    @NotNull(message = BaseConst.BASEMSG_PREFIX + "请输入用户的id")
    private Integer userId;

    @NotNull(message = BaseConst.BASEMSG_PREFIX + "请输入组织的id")
    private Integer organizationId;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;
}