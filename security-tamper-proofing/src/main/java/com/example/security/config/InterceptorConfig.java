package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName : InterceptorCOnfig
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-30 09:32
 * @Version: 1.0.0
 */
@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
    }

}
