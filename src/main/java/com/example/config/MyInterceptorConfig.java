package com.example.config;

import com.example.interceptor.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/07/06/下午9:43
 * @Description:
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private AuthorizeInterceptor authorizeInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(authorizeInterceptor);
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/auth/**"
                );
    }
}
