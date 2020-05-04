package org.zzr.mynote.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IUserInfoService;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
@RestController
@RequestMapping("//user-info")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("getOne")
    public void getOne(){
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().gt(UserInfo::getEmail, "aaa@"));
        System.out.println(userInfo);
    }
}
