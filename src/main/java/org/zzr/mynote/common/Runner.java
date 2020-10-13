package org.zzr.mynote.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zzr.mynote.common.configuration.CommonConfig;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.util.RequestUtils;

import javax.annotation.Resource;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */
@Component
@Order(value = 1)
public class Runner implements ApplicationRunner {
    @Resource
    private CommonConfig config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PublicConstant.port = config.port;
        PublicConstant.serviceUrl = "http://127.0.0.1:" + config.port;
        /*PublicConstant.appName = config.appName;
        PublicConstant.mailServerHost = config.mailServerHost;
        PublicConstant.mailServerUser = config.mailServerUser;
        PublicConstant.mailServerPassword = config.mailServerPassword;*/

        PublicConstant.appName = config.appName;
        PublicConstant.nginxPath = config.nginxPath;
        PublicConstant.webUrl = config.webUrl;

        PublicConstant.localImgPath = config.localImgPath;

        PublicConstant.webImgPath = config.webImgPath;


    }

}
