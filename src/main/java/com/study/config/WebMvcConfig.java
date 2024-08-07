package com.study.config;

import com.study.interceptor.AdminCheckInterceptor;
import com.study.interceptor.LoggerInterceptor;
import com.study.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/uploads/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**/*.do")
                .excludePathPatterns("/log*");

        // AdminInterceptor 추가
        registry.addInterceptor(new AdminCheckInterceptor())
                .addPathPatterns("/admin/**"); // /admin 경로에 적용
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/develop/upload-files/");
    }

}
