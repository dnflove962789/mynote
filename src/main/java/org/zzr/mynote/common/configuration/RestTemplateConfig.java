package org.zzr.mynote.common.configuration;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {

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
