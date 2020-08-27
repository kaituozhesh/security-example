package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * @ClassName SecurityConfig
 * @Description
 * @Author kaituozhesh
 * @Date 2020/8/27 16:57
 * @Version V1.0.0
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder：Spring Security 提供的加密工具，可快速实现加密加盐
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启登录配置
        http.authorizeRequests()
                // 访问/index 这个接口  需要具备 ADMIN 角色
                .antMatchers("/index").hasRole("ADMIN")
                // 允许匿名的url 可以理解为放行接口 多个接口使用 , 分割
                .antMatchers("/", "/home").permitAll()
                // 其余所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                // 设置登录认证页面
                .formLogin().loginPage("/login")
                // 登录成功后的处理接口 - 方式一
                // .loginProcessingUrl("/home")
                // 自定义登录用户名和密码属性名，默认为 username和password
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录成功后的处理器  方式二
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("登录成功。。");
                    writer.flush();
                })
                // 登录失败的回调
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("登录失败...");
                    writer.flush();
                })
                // 和表单登录相关的接口统统都直接通过
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                // 配置注销成功的回调
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write("注销成功");
                    writer.flush();
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                // 关闭CSRF跨域
                .csrf().disable();
    }

    /**
     * 以下实现为基于内存的   把用户设置在内存中
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                // 指定加密方式
                .passwordEncoder(passwordEncoder())
                // 在内存中配置用户，配置多个用户调用`and()`方法
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("test").password(passwordEncoder().encode("123456")).roles("USER");
    }
}
