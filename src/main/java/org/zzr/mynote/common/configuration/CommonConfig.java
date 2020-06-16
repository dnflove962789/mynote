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
    @Value("${server.port:8123}")
    public String port;

    @Value("${server.address:http://127.0.0.1}")
    public String address;

    @Value("${nginx.path}")
    public String nginxPath;

    @Value("${nginx.url}")
    public String nginxUrl;

    @Value("${img.path}")
    public String imgPath;

    @Value("${thum.path}")
    public String thumPath;
}
