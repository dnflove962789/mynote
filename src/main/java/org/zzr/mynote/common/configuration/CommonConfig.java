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

    @Value("http://127.0.0.1")
    public String address;

    /**
     * nginx 文件目录
     */
    @Value("${nginx.path}")
    public String nginxPath;

    /**
     * nginx 服务器访问地址
     */
    @Value("http://localhost:8081/mynote/")
    public String nginxUrl;

    /**
     * 原图存放的相对目录
     */
    @Value("${img.path}")
    public String imgPath;

    /**
     * 缩略图存放的相对目录
     */
    @Value("${thum.path}")
    public String thumPath;

    @Value("${web.url}")
    public String webUrl;
}
