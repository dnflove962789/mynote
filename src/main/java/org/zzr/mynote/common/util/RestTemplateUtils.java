package org.zzr.mynote.common.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.zzr.mynote.common.HttpsClientRequestFactory;


public class RestTemplateUtils {

    public static RestTemplate getHttpsTemplate(){
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        return init(restTemplate);
    }
    public static RestTemplate getTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return init(restTemplate);
    }

    private static RestTemplate init(RestTemplate restTemplate){
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        return restTemplate;
    }
}
