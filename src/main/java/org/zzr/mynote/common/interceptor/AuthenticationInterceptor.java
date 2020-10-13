package org.zzr.mynote.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zzr.mynote.common.annotation.AdminUserLoginToken;
import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IUserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            //处理普通用户
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            String token = request.getHeader("token");// 从 http 请求头中取出 token

            if(userLoginToken.required()){
               return verifyToken(token);
            }
        }else if(method.isAnnotationPresent(AdminUserLoginToken.class)){
            //处理admin用户
            AdminUserLoginToken adminUserLoginToken = method.getAnnotation(AdminUserLoginToken.class);
            String token = request.getHeader("adminToken");// 从 http 请求头中取出 token
            if(adminUserLoginToken.required()){
                return verifyToken(token);
            }
        }
        return true;

    }

    /**
     * 验证token
     * @param token
     * @return
     */
    private Boolean verifyToken(String token){
        // 执行认证
        if(token == null){
            throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userInfo.getPassword()))
                .withSubject(PublicConstant.appName)
                .build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }
        return true;
    }
}
