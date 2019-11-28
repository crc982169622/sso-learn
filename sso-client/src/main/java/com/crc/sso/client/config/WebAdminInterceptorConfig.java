package com.crc.sso.client.config;

import com.crc.sso.client.interceptor.WebAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: chenrencun
 * @Date: 2019/11/26 9:44
 * @Description: 描述
 */
@Configuration
public class WebAdminInterceptorConfig implements WebMvcConfigurer {

    //将拦截器设置为Bean,在拦截其中才能使用@AutoWired注解自动注入
    @Bean
    WebAdminInterceptor webAdminInterceptor() {
        return new WebAdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webAdminInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static");
    }
}
