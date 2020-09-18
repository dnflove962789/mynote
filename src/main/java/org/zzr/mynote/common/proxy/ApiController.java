package org.zzr.mynote.common.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.zzr.mynote.common.response.Response;

import org.zzr.mynote.common.util.RequestUtils;
import org.zzr.mynote.common.util.ResponseUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yangkaile
 * @date 2019-08-28 09:05:54
 * 接口转发
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class ApiController {

    @Autowired
    HttpServletRequest request;

    //@Autowired
    //RestTemplate restTemplate;


    /**
     * json 格式的GET请求
     * @return
     */
    @GetMapping
    public ResponseEntity get(){
        System.out.println(request);
        Map<String,Object> params = RequestUtils.getBodyParams(request);
        //return RequestUtils.doGet(request,params,restTemplate);
        return null;
    }


    /**
     * JSON  形式的 POST 请求
     * @return
     */
    @PostMapping
    public ResponseEntity post(){
        Map<String,Object> params = RequestUtils.getBodyParams(request);
        //return RequestUtils.doPost(request,params,restTemplate);
        return null;
    }

}
