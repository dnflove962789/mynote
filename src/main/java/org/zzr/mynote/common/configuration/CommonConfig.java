package org.zzr.mynote.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 项目配置文件，从application.yml中加载
 */
@Configuration
@PropertySource("classpath:application.yml")
public class CommonConfig {

    @Value("${server.port}")
    public String port;

    @Value("${nginx.localImgPath}")
    public  String localImgPath;

    @Value("${nginx.webImgPath}")
    public  String webImgPath;

    @Value("${web.url}")
    public String webUrl;

    @Value("${app.name}")
    public String appName;

    /**
     * nginx 文件目录
     */
    @Value("${nginx.path}")
    public String nginxPath;



}
