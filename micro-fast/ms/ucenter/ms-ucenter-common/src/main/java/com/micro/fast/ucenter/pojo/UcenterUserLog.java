package com.micro.fast.ucenter.pojo;

import java.io.Serializable;

public class UcenterUserLog implements Serializable {
    private Integer id;

    private Integer userId;

    private String ip;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private byte[] content;

    private byte[] agent;

    private static final long serialVersionUID = 1L;

    public UcenterUserLog(Integer id, Integer userId, String ip, Long ctime, Long uptime, Integer version, byte[] content, byte[] agent) {
        this.id = id;
        this.userId = userId;
        this.ip = ip;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
        this.content = content;
        this.agent = agent;
    }

    public UcenterUserLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getAgent() {
        return agent;
    }

    public void setAgent(byte[] agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", ip=").append(ip);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append(", content=").append(content);
        sb.append(", agent=").append(agent);
        sb.append("]");
        return sb.toString();
    }
}