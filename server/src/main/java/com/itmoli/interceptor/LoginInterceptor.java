package com.itmoli.interceptor;

import com.itmoli.constant.MsgConstant;
import com.itmoli.exception.JwtException;
import com.itmoli.query.Thread;
import com.itmoli.query.ThreadOne;
import com.itmoli.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    //目标资源执行前操作，
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");

        log.info("token:{}",token);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getAdminSecretKey())//密钥
                    .parseClaimsJws(token)//token
                    .getBody();

            ThreadLocal threadLocal = Thread.getThreadLocal();
            threadLocal.set(claims.get("account"));
            ThreadLocal threadLocalOne = ThreadOne.getThreadLocal();
            threadLocalOne.set(claims.get("id"));

            log.info("线程储存局部变量account：{}",threadLocal.get());
            log.info("线程储存局部变量id：{}",threadLocalOne.get());


            return true;
        } catch (Exception e) {
            throw new JwtException(MsgConstant.TOKEN_ERR);
        }

    }


    //目标资源执行滞后只执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    //目标渲染之后之行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
