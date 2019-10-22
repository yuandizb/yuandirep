package com.micro.fast.security.app.master;

import com.micro.fast.security.app.validate.code.config.param.ValidateCodeProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用于从application.yml配置文件中加载以ms.security为前缀的配置属性， ms spring security 配置参数
 * @author lsy
 */
@ConfigurationProperties(prefix = "ms.security")
@Configuration
@Getter
@Setter
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();
}
