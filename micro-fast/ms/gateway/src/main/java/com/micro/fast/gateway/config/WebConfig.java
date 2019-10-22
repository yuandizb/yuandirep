package com.micro.fast.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网关web配置类
 * @author lsy
 */
@Configuration
public class WebConfig{
    /**
     * 网关支持跨域配置，跨域问题需要在网关解决,cors需要浏览器和服务端同时解决.
     * @return
     */
    @Bean
    @Order(Integer.MAX_VALUE)
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许cookie跨域，在cookie跨域的时候，服务端进行设置，浏览器端也要进行显式设置是否发送。
//        corsConfiguration.setAllowCredentials(true);
        //能给这个服务器发起请求的url，当这个值设置为*的时候，cookie就不能随着请求进行发送了。因为cookie必须是同源的。
        corsConfiguration.addAllowedOrigin("*");
        //允许的请求头为全部
        corsConfiguration.addAllowedHeader("*");
        //允许的请求类型
        corsConfiguration.addAllowedMethod("*");
        //预请求的校验时间间隔为6小时
        corsConfiguration.setMaxAge(60*60*6L);
        //允许请求的路径
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
