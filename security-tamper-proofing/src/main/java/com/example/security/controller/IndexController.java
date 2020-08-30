package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : IndexController
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-30 11:21
 * @Version: 1.0.0
 */
@RestController
public class IndexController {

    @GetMapping("/xx")
    public Object getUser(HttpServletRequest request, @RequestParam String aa) {
        String userId = request.getHeader("userId");
        return userId;
    }

}
