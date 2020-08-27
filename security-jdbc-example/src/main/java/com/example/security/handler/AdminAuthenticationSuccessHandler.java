package com.example.security.handler;

import com.example.security.domain.SecurityUser;
import com.example.security.domain.SysUser;
import com.example.security.utils.ApiResult;
import com.example.security.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : AdminAuthenticationSuccessHandler
 * @Description :
 * @Author : kaituozhesh
 * @Date: 2020-08-27 20:33
 * @Version: 1.0.0
 */
@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        SysUser user = new SysUser();
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        user.setToken(securityUser.getCurrentUserInfo().getToken());
        ResponseUtils.out(response, ApiResult.ok("登录成功!", user));
    }
}
