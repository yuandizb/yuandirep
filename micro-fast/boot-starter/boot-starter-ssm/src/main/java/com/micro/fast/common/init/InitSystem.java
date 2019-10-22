package com.micro.fast.common.init;

import com.micro.fast.common.init.properties.SqlInitProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * 单一数据源时初始化数据库表结构和数据
 * 如果配置多个数据源，按此自行添加
 * @author lsy
 */
@Component
@Slf4j
public class InitSystem implements CommandLineRunner {

    @Autowired(required = false)
    private Map<String,SqlInitProperties> sqlInitPropertiesMap;

    /**
     * 执行初始化sql
     * @param driver 数据库驱动全类名
     * @param url 数据库连接url
     * @param username 数据库连接的用户名
     * @param password 数据库连接的密码
     * @param sqlPath sql脚本的路径
     */
    private void runInitSql(String driver,String url,String username,String password,String sqlPath){
        try {
            // 执行sql脚本
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Resources.setCharset(Charset.forName("utf-8"));
            Reader resourceAsReader = Resources.getResourceAsReader(sqlPath);
            scriptRunner.runScript(resourceAsReader);
            scriptRunner.closeConnection();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if ( sqlInitPropertiesMap != null) {
            // 初始化数据表
            sqlInitPropertiesMap.forEach((key,value) -> {
                if (value != null) {
                    runInitSql(value.getDriverClassName(),value.getUrl(),value.getUsername(),value.getPassword(),value.getSqlPath());
                }
            });
        }
    }
}
