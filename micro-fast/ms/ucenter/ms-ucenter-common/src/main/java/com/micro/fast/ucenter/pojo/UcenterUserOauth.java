package com.micro.fast.ucenter.pojo;

import java.io.Serializable;

public class UcenterUserOauth implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer oauthId;

    private Byte status;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private byte[] openId;

    private static final long serialVersionUID = 1L;

    public UcenterUserOauth(Integer id, Integer userId, Integer oauthId, Byte status, Long ctime, Long uptime, Integer version, byte[] openId) {
        this.id = id;
        this.userId = userId;
        this.oauthId = oauthId;
        this.status = status;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
        this.openId = openId;
    }

    public UcenterUserOauth() {
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

    public Integer getOauthId() {
        return oauthId;
    }

    public void setOauthId(Integer oauthId) {
        this.oauthId = oauthId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public byte[] getOpenId() {
        return openId;
    }

    public void setOpenId(byte[] openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", oauthId=").append(oauthId);
        sb.append(", status=").append(status);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append(", openId=").append(openId);
        sb.append("]");
        return sb.toString();
    }
}