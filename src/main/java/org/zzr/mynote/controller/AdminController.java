package org.zzr.mynote.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzr.mynote.common.annotation.AdminUserLoginToken;
import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.DateUtil;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.entity.LoginLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.ILoginLogService;
import org.zzr.mynote.service.IUserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * 管理员功能
 * @author yangkaile
 * @date 2020-04-23 10:37:24
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class AdminController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ILoginLogService loginLogService;

    @AdminUserLoginToken
    @GetMapping("/userList")
    public ResponseEntity getUserList(Integer current,Integer size,String email){
        Integer userId = TokenUtils.getAdminUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId, current, size)){
            return Response.badRequest();
        }
        IPage<UserInfo> userInfoPage = userInfoService.page(new Page<>(current,size), new LambdaQueryWrapper<UserInfo>()
        .eq(StringUtils.isNotEmpty(email), UserInfo::getEmail, email));
        return Response.ok(new ResultData().success().data(userInfoPage));
    }

    @AdminUserLoginToken
    @GetMapping("/userCounter")
    public ResponseEntity getUserCounter(String email){
        int count = userInfoService.count(new LambdaQueryWrapper<UserInfo>()
                .like(StringUtils.isNotEmpty(email), UserInfo::getEmail, email));

        return Response.ok(new ResultData().success().data(count));
    }

    @AdminUserLoginToken
    @GetMapping("/loginLogByDay")
    public ResponseEntity getLoginLogByDay(String startDate,String endDate) throws Exception {
        if(StringUtils.isEmpty(startDate)){
            return Response.badRequest();
        }
        throw new Exception();
        //return Response.ok(loginLogService.getCountByDay(startDate, endDate));
    }

    @AdminUserLoginToken
    @GetMapping("/loginLogByMonth")
    public ResponseEntity getLoginLogByMonth(String startDate,String endDate){
        if(StringUtils.isEmpty(startDate)){
            return Response.badRequest();
        }
        return Response.ok(loginLogService.getCountByMonth(startDate, endDate));
    }

    @AdminUserLoginToken
    @GetMapping("/registerLogByDay")
    public ResponseEntity getRegisterLogByDay(String startDate,String endDate){
        if(StringUtils.isEmpty(startDate)){
            return Response.badRequest();
        }
        //registerLogService.getCountByDay(startDate,endDate)
        return Response.ok();
    }

    @AdminUserLoginToken
    @GetMapping("/registerLogByMonth")
    public ResponseEntity getRegisterLogByMonth(String startDate,String endDate){
        if(StringUtils.isEmpty(startDate)){
            return Response.badRequest();
        }
        //registerLogService.getCountByMonth(startDate,endDate)
        return Response.ok();
    }
}
