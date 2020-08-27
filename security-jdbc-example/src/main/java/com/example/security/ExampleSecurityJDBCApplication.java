package com.example.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName : ExampleSecurityJDBCApplication
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-27 20:19
 * @Version: 1.0.0
 */
@MapperScan(basePackages = {"com.example.security.mapper"})
@SpringBootApplication
public class ExampleSecurityJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSecurityJDBCApplication.class, args);
    }
}
