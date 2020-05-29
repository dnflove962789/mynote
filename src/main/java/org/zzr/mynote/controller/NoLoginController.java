package org.zzr.mynote.controller;

//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.Console;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IEmailLogService;
import org.zzr.mynote.service.IUserInfoService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/no-login")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class NoLoginController {

    @Resource
    private IEmailLogService emailLogService;

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 发送邮件验证码
     * @param email
     * @return ResponseEntity
     */
    @GetMapping("/getCodeForRegister")
    public ResponseEntity getCodeForRegister(String email){
        if(StringUtils.isEmpty(email)){
            return Response.badRequest();
        }
        //查找用户
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getEmail, email).eq(UserInfo::getType, PublicConstant.DEFAULT_USER_TYPE));
        if(userInfo != null){
            return Response.ok(new ResultData().fail().message("该邮箱已被注册"));
        }
       //发送邮件
        ResultData resultData = emailLogService.sendSimpleMail(email);
        return Response.ok(resultData);
    }

    /**
     * 注册
     * @param params
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Map<String,String> params){
        Console.info("register",params);
        String email = params.get("email");
        String password = params.get("password");
        String code = params.get("code");
        if(StringUtils.isEmpty(password, code) || !StringUtils.isEmail(email)){
            return Response.badRequest();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setType(PublicConstant.DEFAULT_USER_TYPE);
        //用户注册
        ResultData resultData = userInfoService.registerUser(userInfo, code);
        return Response.ok(resultData);
    }

    /**
     * 账号密码登录
     * @param params
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String,String> params){
        String email = params.get("email");
        String password = params.get("password");
        int type = Integer.parseInt(params.get("type"));
        if(StringUtils.isEmpty(password) || !StringUtils.isEmail(email)){
            return Response.badRequest();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setType(type);
        return Response.ok(userInfoService.login(userInfo));
    }

    /**
     * 账号密码登录
     * @param params
     * @return
     */
    @PostMapping("/loginWithCode")
    public ResponseEntity loginWithCode(@RequestBody Map<String,String> params){
        String email = params.get("email");
        String code = params.get("code");
        int type = Integer.parseInt(params.get("type"));
        if(StringUtils.isEmpty(code) || !StringUtils.isEmail(email)){
            return Response.badRequest();
        }
        return Response.ok(userInfoService.loginWithCode(email, type, code));
    }


}
