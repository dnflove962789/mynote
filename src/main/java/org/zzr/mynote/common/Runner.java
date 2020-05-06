package org.zzr.mynote.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zzr.mynote.common.configuration.CommonConfig;
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
        RequestUtils.port = config.port;
        RequestUtils.address = config.address;
    }
}
