package org.zzr.mynote.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity setUserCard(Integer kellerUserId, String nickName, String email, String label){
        if(kellerUserId != null && StringUtils.isNotEmpty(nickName,email,label)){
            return Response.ok(
                    userCardService.setUserCard(kellerUserId,nickName,email,label));
        }
        return Response.badRequest();
    }
}
