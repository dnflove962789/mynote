package org.zzr.mynote;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.zzr.mynote.common.util.RestTemplateUtils;

@SpringBootApplication
public class MynoteApplication {

	/*@Bean*/
	/**
	 * 引入RestTemplate Bean
	 * 用来进行服务间的Http通信
	 * 同时重新定义其解析时用到的字符集，防止中文乱码
	 */
	/*RestTemplate restTemplate(){
		return RestTemplateUtils.getHttpsTemplate();

	}*/

	public static void main(String[] args) {
		SpringApplication.run(MynoteApplication.class, args);
	}

}
