package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Description
 * @Author kaituozhesh
 * @Date 2020/8/27 16:52
 * @Version V1.0.0
 **/
@RestController
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "Hello World ~";
    }

}
