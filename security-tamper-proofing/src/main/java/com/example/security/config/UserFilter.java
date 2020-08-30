package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName : UserFilter
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-30 11:29
 * @Version: 1.0.0
 */
@Component
@Order(1)
@WebFilter(filterName = "userFilter", urlPatterns = "/*")
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        RequestWrapper requestWrapper = new RequestWrapper(req);
        //获得请求参数中的token值
        requestWrapper.addHeader("userId", "1111");
        chain.doFilter(requestWrapper, response);
    }
}