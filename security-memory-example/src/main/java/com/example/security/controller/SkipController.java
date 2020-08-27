package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName SkipController
 * @Description
 * @Author kaituozhesh
 * @Date 2020/8/27 18:46
 * @Version V1.0.0
 **/
@Controller
public class SkipController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
