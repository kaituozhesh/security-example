package com.example.security.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.example.security.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName : AuthInterceptor
 * @Description :
 * 所有请求头都将带有三个参数
 * - sign：对所有参数和timestamp签名
 * - timestamp：当前时间戳
 * - access-token：唯一表示用于token
 * @Author : kaituozhesh
 * @Date: 2020-08-30 09:05
 * @Version: 1.0.0
 */
@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    /**
     * 验证签名的key
     */
    private static final String accessKey = "finance-key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        // 请求时的实现戳
        String timestamp = request.getHeader("timestamp");
        if (stampVerify(timestamp)) {
            returnFail("签名已过期", response);
            return false;
        }

        if (signVerify(requestWrapper)) {
            returnFail("签名错误", response);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(SecureUtil.md5(SecureUtil.md5("aa123") + accessKey));
    }

    /**
     * 验证是否重放请求
     *
     * @param timestamp
     * @return
     */
    private boolean stampVerify(String timestamp) {
        if (StringUtils.isEmpty(timestamp) || !timestamp.matches("^\\d+$")) {
            return true;
        }
        return timestamp.compareTo(String.valueOf(System.currentTimeMillis() - 60 * 1000)) < 0;
    }

    /**
     * 签名验证
     *
     * @return
     */
    private boolean signVerify(RequestWrapper requestWrapper) {

        String sign = requestWrapper.getHeader("sign");
        if (StringUtils.isEmpty(sign)) {
            return true;
        }
        // 获取post请求json参数
        String body = requestWrapper.getBody();
        Map<String, Object> reqMap = JSONUtils.jsonToMap(body);
        // 获取get请求参数
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        parameterMap.forEach((k, v) -> reqMap.put(k, v[0]));
        StringBuilder builder = new StringBuilder();
        reqMap.forEach((k, v) -> builder.append(k + v));
        if (!requestWrapper.getHeader("sign").equals(SecureUtil.md5(SecureUtil.md5(builder.toString()) + accessKey))) {
            return true;
        }
        return false;
    }


    /**
     * 非法请求
     */
    private void returnFail(String message, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(message);
    }
}
