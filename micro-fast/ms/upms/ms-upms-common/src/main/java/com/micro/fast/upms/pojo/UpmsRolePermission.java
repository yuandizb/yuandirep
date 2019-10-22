package com.micro.fast.upms.pojo;

import java.io.Serializable;

public class UpmsRolePermission implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UpmsRolePermission(Integer id, Integer roleId, Integer permissionId, Long ctime, Long uptime, Integer version) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UpmsRolePermission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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
        sb.append(", roleId=").append(roleId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}