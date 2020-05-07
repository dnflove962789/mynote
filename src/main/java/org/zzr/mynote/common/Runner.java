package org.zzr.mynote.common;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

    @Bean
    /**
     * 引入RestTemplate Bean
     * 用来进行服务间的Http通信
     * 同时重新定义其解析时用到的字符集，防止中文乱码
     */
    RestTemplate restTemplate(){
        //创建一个 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();
        //清除掉原有的消息转换器，因为这些转换器处理中文字符的能力有限，比较容易出现乱码
        restTemplate.getMessageConverters().clear();
        //为 RestTemplate 实例指定 FastJson 的消息转换器
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        //返回配置好的 RestTemplate 实例
        return restTemplate;
    }
}
