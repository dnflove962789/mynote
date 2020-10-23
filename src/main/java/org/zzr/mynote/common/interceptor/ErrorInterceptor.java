package org.zzr.mynote.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.util.Console;
import org.zzr.mynote.common.util.ErrorLogUtils;
import org.zzr.mynote.common.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ErrorInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Console.println("preHandle",RequestUtils.getRealIp(request),PublicConstant.address);
        boolean check = PublicConstant.address.equals(RequestUtils.getRealIp(request));
        if(!check){
            ErrorLogUtils.accessErrorLog(request);
        }
        return check;
    }
}
