package org.zzr.mynote.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.service.IUserCardService;

/**
 * <p>
 * 用户名片表 前端控制器
 * </p>
 *
 * @author zzr
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/user-card")
public class UserCardController {

    @Autowired
    private IUserCardService userCardService;
    /**
     * 设置用户名片接口
     */
    @PostMapping
    public ResponseEntity setUserCard(Integer userId, String nickName, String email, String label){
        if(userId != null && StringUtils.isNotEmpty(nickName,email,label)){
            return Response.ok(
                    userCardService.setUserCard(userId,nickName,email,label));
        }
        return Response.badRequest();
    }

    @GetMapping
    public ResponseEntity getUserCard(Integer userId){
        if(userId != null){
            return Response.ok(userCardService.getUserCard(userId));
        }
        return Response.badRequest();
    }
}
