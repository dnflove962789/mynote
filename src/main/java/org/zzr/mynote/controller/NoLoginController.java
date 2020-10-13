package org.zzr.mynote.controller;

//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.Console;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.common.util.RequestUtils;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IEmailLogService;
import org.zzr.mynote.service.IUserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/no-login")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class NoLoginController {

    @Resource
    private IEmailLogService emailLogService;

    @Resource
    private IUserInfoService userInfoService;

    @Resource
    private HttpServletRequest request;

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
     * @param
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserInfo userInfo){
        if(StringUtils.isEmpty(userInfo.getPassword(), userInfo.getCode()) || !StringUtils.isEmail(userInfo.getEmail())){
            return Response.badRequest();
        }
        userInfo.setType(PublicConstant.DEFAULT_USER_TYPE);
        //用户注册
        ResultData resultData = userInfoService.registerUser(userInfo,  userInfo.getCode(), RequestUtils.getRealIp(request));
        return Response.ok(resultData);
    }

    /**
     * 账号密码登录
     * @param
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserInfo userInfo){
        if(StringUtils.isEmpty(userInfo.getPassword()) || !StringUtils.isEmail(userInfo.getEmail())){
            return Response.badRequest();
        }

        return Response.ok(userInfoService.login(userInfo, RequestUtils.getRealIp(request)));
    }

    /**
     * 账号邮件码登录
     * @param params
     * @return
     */
    @PostMapping("/loginWithCode")
    public ResponseEntity loginWithCode(@RequestBody Map<String,String> params){
        String email = params.get("email");
        String code = params.get("code");
        int type = Integer.parseInt(params.get("type"));
        if(StringUtils.isEmpty(code) || !StringUtils.isEmail(email) || !PublicConstant.isUserType(type)){
            return Response.badRequest();
        }
        EmailLog emailLog = new EmailLog();
        emailLog.setEmail(email);
        emailLog.setType(PublicConstant.LOGIN_TYPE);
        emailLog.setCode(code);
        return Response.ok(userInfoService.loginWithCode(emailLog, type));
    }

    /**
     * 发送邮件重置密码
     * @param userInfo
     * @return
     */
    @PostMapping("/sendResetPasswordEmail")
    public ResponseEntity sendResetPasswordEmail(@RequestBody UserInfo userInfo){

        int type ;
        if(userInfo.getType() == null){
            type = PublicConstant.DEFAULT_USER_TYPE;
        }else {
            type= userInfo.getType();
        }
        if(StringUtils.isEmail(userInfo.getEmail())){
            ResultData resultData = userInfoService.sendResetPasswordEmail(userInfo.getEmail(), type);
            return Response.ok(resultData);

        }
        return Response.badRequest();
    }

    /**
     * 通过邮件重置密码，上送参数中是密码
     * 从请求中解析 JWT
     * @param userInfo
     * @return
     */
    @PostMapping("/resetPasswordByEmail")
    public ResponseEntity resetPasswordByEmail(@RequestBody UserInfo userInfo){
        if(userInfo.getToken() == null){
            return Response.badRequest();
        }
        return Response.ok(userInfoService.resetPasswordByEmail(userInfo.getToken(),userInfo.getPassword()));

    }

}
