package com.example.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.security.common.MultiReadHttpServletRequest;
import com.example.security.config.Constants;
import com.example.security.config.CusAuthenticationManager;
import com.example.security.domain.SysUser;
import com.example.security.handler.AdminAuthenticationFailureHandler;
import com.example.security.handler.AdminAuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : AdminAuthenticationProcessingFilter
 * @Description : 自定义密码校验过滤器
 * @Author : kaituozhesh
 * @Date: 2020-08-27 20:20
 * @Version: 1.0.0
 */
@Slf4j
@Component
public class AdminAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * @param authenticationManager:             认证管理器
     * @param adminAuthenticationSuccessHandler: 认证成功处理
     * @param adminAuthenticationFailureHandler: 认证失败处理
     */
    public AdminAuthenticationProcessingFilter(CusAuthenticationManager authenticationManager, AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler, AdminAuthenticationFailureHandler adminAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE)) {
            throw new AuthenticationServiceException("请求头类型不支持: " + request.getContentType());
        }
        UsernamePasswordAuthenticationToken authRequest;
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
        // 将前端传递的数据转换成jsonBean数据格式
        SysUser user = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), SysUser.class);
        authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
