package com.example.security.config;

import com.example.security.filter.AdminAuthenticationProcessingFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName : SecurityConfig
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-27 20:19
 * @Version: 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 用户密码校验过滤器
     */
    private final AdminAuthenticationProcessingFilter adminAuthenticationProcessingFilter;

    public SecurityConfig(AdminAuthenticationProcessingFilter adminAuthenticationProcessingFilter) {
        this.adminAuthenticationProcessingFilter = adminAuthenticationProcessingFilter;
    }

    /**
     * 权限配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();

        // 禁用CSRF 开启跨域
        http.csrf().disable().cors();

        // 标识只能在 服务器本地ip[127.0.0.1 或 localhost]访问`/home`接口，其他ip地址无法访问
        registry.antMatchers("/home").hasIpAddress("127.0.0.1");
        // 允许匿名url 可以理解为放行接口 - 多个接口使用,分割
        registry.antMatchers("/login", "/index");

        registry.antMatchers(HttpMethod.OPTIONS, "/**").denyAll();
        // 自动登录 -  cookie存储方式
        registry.and().rememberMe();
        // 其余所有请求都需要认证
        registry.anyRequest().authenticated();
        // 防止iframe 造成跨域
        registry.and().headers().frameOptions().disable();

        // 自定义过滤器认证用户密码
        http.addFilterAt(adminAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
