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
@RequestMapping("/form")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class FormController {

    @Resource
    HttpServletRequest request;

    //@Autowired
    //RestTemplate restTemplate;

    /**
     * 表单形式的GET请求
     * @return
     */
    @GetMapping
    public ResponseEntity get(@RequestBody Map<String, Object> params,  HttpServletRequest request){
        System.out.println(request.getParameterMap());
        //Map<String,Object> params = RequestUtils.getParam(request);
        //return RequestUtils.doGet(request,params,restTemplate);
        return null;
    }


    /**
     * 表单形式的 POST 请求
     * @return
     */
    @PostMapping
    public ResponseEntity post(){
        Map<String,Object> params = RequestUtils.getParam(request);
        //return RequestUtils.doPost(request,params,restTemplate);
        return null;
    }
}
