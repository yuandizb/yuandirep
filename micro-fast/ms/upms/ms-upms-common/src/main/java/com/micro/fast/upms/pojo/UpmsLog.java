package com.micro.fast.upms.pojo;

import java.io.Serializable;

public class UpmsLog implements Serializable {
    private Integer id;

    private String descirption;

    private String username;

    private Long startTime;

    private Integer spendTime;

    private String basePath;

    private String uri;

    private String url;

    private String method;

    private String userAgent;

    private String ip;

    private String permissions;

    private Long ctime;

    private Long uptime;

    private Integer version;

    private String parameter;

    private String result;

    private static final long serialVersionUID = 1L;

    public UpmsLog(Integer id, String descirption, String username, Long startTime, Integer spendTime, String basePath, String uri, String url, String method, String userAgent, String ip, String permissions, Long ctime, Long uptime, Integer version, String parameter, String result) {
        this.id = id;
        this.descirption = descirption;
        this.username = username;
        this.startTime = startTime;
        this.spendTime = spendTime;
        this.basePath = basePath;
        this.uri = uri;
        this.url = url;
        this.method = method;
        this.userAgent = userAgent;
        this.ip = ip;
        this.permissions = permissions;
        this.ctime = ctime;
        this.uptime = uptime;
        this.version = version;
        this.parameter = parameter;
        this.result = result;
    }

    public UpmsLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescirption() {
        return descirption;
    }

    public void setDescirption(String descirption) {
        this.descirption = descirption == null ? null : descirption.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath == null ? null : basePath.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", descirption=").append(descirption);
        sb.append(", username=").append(username);
        sb.append(", startTime=").append(startTime);
        sb.append(", spendTime=").append(spendTime);
        sb.append(", basePath=").append(basePath);
        sb.append(", uri=").append(uri);
        sb.append(", url=").append(url);
        sb.append(", method=").append(method);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", ip=").append(ip);
        sb.append(", permissions=").append(permissions);
        sb.append(", ctime=").append(ctime);
        sb.append(", uptime=").append(uptime);
        sb.append(", version=").append(version);
        sb.append(", parameter=").append(parameter);
        sb.append(", result=").append(result);
        sb.append("]");
        return sb.toString();
    }
}