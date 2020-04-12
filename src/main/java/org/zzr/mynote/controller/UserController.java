package org.zzr.mynote.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    // ResponseEntity 返回的数据类型为标准格式的 HTTP 应答
    public ResponseEntity getAll(){
        List<UserInfo> list = userService.getAll();
        // 返回的 HTTP 应答码为 200 OK
        return ResponseEntity.ok()
                // 返回的 HTTP 内容类型为 JSON 格式
                .contentType(MediaType.APPLICATION_JSON)
                // 将查询到的用户列表放在 HTTP Body 中返回
                .body(list);
    }
}
