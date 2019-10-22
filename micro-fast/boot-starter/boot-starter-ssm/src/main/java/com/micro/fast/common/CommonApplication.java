package com.micro.fast.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类,该类将配置于META-INF/spring.factories文件中用于被EnableAutoConfiguration注解所识别实现自动配置
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.micro.fast.common"})
public class CommonApplication {

}