package org.zzr.mynote.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzr.mynote.common.response.MyResponseEntity;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:UserController
 * Version:2020/4/11 & 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    // 注入业务层组件 UserService
    @Resource
    private UserService userService;

    @GetMapping
    // MyResponseEntity 返回的数据类型为标准格式的 HTTP 应答
    public MyResponseEntity getAll() throws Exception {
        List<UserInfo> list = userService.getAll();
        return MyResponseEntity.success(list);

    }
}
