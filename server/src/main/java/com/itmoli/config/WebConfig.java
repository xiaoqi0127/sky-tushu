package com.itmoli.config;

import com.itmoli.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;


    public void addInterceptors(InterceptorRegistry registry) {

        log.info("注册拦截器");

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/**/login","/error");
    }
}
