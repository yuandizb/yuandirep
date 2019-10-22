package com.micro.fast.ucenter.pojo;

import java.io.Serializable;

public class UcenterOauth implements Serializable {
    private Integer id;

    private String name;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public UcenterOauth(Integer id, String name, Long ctime, Long uptime, Integer version) {
        this.id = id;
        this.name = name;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
    }

    public UcenterOauth() {
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
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}