package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName ExampleSecurityApplication
 * @Description
 * @Author kaituozhesh
 * @Date 2020/8/27 16:51
 * @Version V1.0.0
 **/
@SpringBootApplication
public class ExampleSecurityApplication {

    /**
     * 运行代码后访问localhost:8080/index 默认会跳转到认证页面，当认证成功后会跳转到请求页面
     * 在不进行任何配置的情况下 默认用于为user 默认密码为启动时控制台打印的 Using generated security password: `ab6cecfc-369e-4abf-a1cb-545c320ea0cc`
     *
     * 配置用户密码可以通过 配置文件 application.yml 或者 继承于WebSecurityConfigurerAdapter类  重写configure方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ExampleSecurityApplication.class, args);
    }

}
