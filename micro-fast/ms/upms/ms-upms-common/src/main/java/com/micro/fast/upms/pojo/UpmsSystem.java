package com.micro.fast.upms.pojo;

import com.micro.fast.boot.starter.common.response.BaseConst;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UpmsSystem implements Serializable {
    private Integer id;

    private String icon;

    private String banner;

    private String theme;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请填写系统路径")
    private String basepath;

    private Byte status;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请填写系统名称")
    private String name;

    @NotBlank(message = BaseConst.BASEMSG_PREFIX+"请填写系统路由")
    private String route;

    private String title;

    private String description;

    private Long orders;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UpmsSystem(Integer id, String icon, String banner, String theme, String basepath, Byte status, String name, String route, String title, String description, Long orders, Long ctime, Long uptime, Integer version) {
        this.id = id;
        this.icon = icon;
        this.banner = banner;
        this.theme = theme;
        this.basepath = basepath;
        this.status = status;
        this.name = name;
        this.route = route;
        this.title = title;
        this.description = description;
        this.orders = orders;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UpmsSystem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner == null ? null : banner.trim();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getBasepath() {
        return basepath;
    }

    public void setBasepath(String basepath) {
        this.basepath = basepath == null ? null : basepath.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route == null ? null : route.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
        sb.append(", icon=").append(icon);
        sb.append(", banner=").append(banner);
        sb.append(", theme=").append(theme);
        sb.append(", basepath=").append(basepath);
        sb.append(", status=").append(status);
        sb.append(", name=").append(name);
        sb.append(", route=").append(route);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", orders=").append(orders);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}