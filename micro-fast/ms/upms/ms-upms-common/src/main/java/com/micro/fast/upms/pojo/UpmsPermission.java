package com.micro.fast.upms.pojo;

import com.micro.fast.boot.starter.common.response.BaseConst;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class UpmsPermission implements Serializable {
    private Integer id;

    @NotNull(message = BaseConst.BASEMSG_PREFIX+"请选择系统id")
    private Integer systemId;

    private Integer pid;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请输入权限名称")
    private String name;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请输入权限名称并尽可能详尽")
    private String description;

    /**
     * 0 表示数据权限 1 表示视图权限
     */
    @NotNull(message = BaseConst.BASEMSG_PREFIX+"请选择权限类型")
    private Byte type;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请选择权限值")
    private String permissionValue;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请输入权限路径")
    private String uri;

    private String icon;

    private Byte status;

    private Long orders;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UpmsPermission(Integer id, Integer systemId, Integer pid, String name, String description, Byte type, String permissionValue, String uri, String icon, Byte status, Long orders, Long ctime, Long uptime, Integer version) {
        this.id = id;
        this.systemId = systemId;
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.type = type;
        this.permissionValue = permissionValue;
        this.uri = uri;
        this.icon = icon;
        this.status = status;
        this.orders = orders;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UpmsPermission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue == null ? null : permissionValue.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        sb.append(", systemId=").append(systemId);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", type=").append(type);
        sb.append(", permissionValue=").append(permissionValue);
        sb.append(", uri=").append(uri);
        sb.append(", icon=").append(icon);
        sb.append(", status=").append(status);
        sb.append(", orders=").append(orders);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 根据id比较是否相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpmsPermission)) {
            return false;
        }
        UpmsPermission that = (UpmsPermission) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}