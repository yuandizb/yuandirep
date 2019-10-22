package com.micro.fast.common.init.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 运行sql脚本相关的配置
 * @author lsy
 */
@Getter
@Setter
public class SqlInitProperties {


    /**
     * 数据库连接用户名
     */
    private String username;

    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 数据库连接驱动全类名
     */
    private String driverClassName;

    /**
     * 数据库连接url
     */
    private String url;

    /**
     * 执行脚本的classpath路径
     */
    private String sqlPath;

}
