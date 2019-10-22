package com.micro.fast.upms.pojo;

import com.micro.fast.boot.starter.common.response.BaseConst;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UpmsRole implements Serializable {
    private Integer id;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请填写角色名称")
    private String name;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请填写角色描述")
    private String description;

    private Long orders;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UpmsRole(Integer id, String name, String description, Long orders, Long ctime, Long uptime, Integer version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.orders = orders;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UpmsRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", orders=").append(orders);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}