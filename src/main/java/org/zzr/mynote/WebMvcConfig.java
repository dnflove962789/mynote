package org.zzr.mynote;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.zzr.mynote.common.interceptor.AuthenticationInterceptor;
import org.zzr.mynote.common.interceptor.ErrorInterceptor;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Resource
    private ErrorInterceptor errorInterceptor;

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(errorInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/note-info/**")
                .addPathPatterns("/notes-info/**")
                .addPathPatterns("/userCard/**")
                .addPathPatterns("/upload/**")
                .addPathPatterns("//user-info/**");
    }
}
