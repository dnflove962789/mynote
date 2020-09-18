package org.zzr.mynote.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.entity.UserCard;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IUserCardService;
import org.zzr.mynote.service.IUserInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class UserCardController {

    @Autowired
    private IUserCardService userCardService;

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 设置用户名片接口
     */
    @GetMapping("/setUserCard")
    public ResponseEntity setUserCard(Integer userId, String nickName, String email, String label, @RequestBody Map<String,String> params){
        String ff = params.get("userId");
        if(userId != null && StringUtils.isNotEmpty(nickName,email,label)){
            return Response.ok(
                    userCardService.setUserCard(userId,nickName,email,label));
        }
        return Response.badRequest();
    }

    @UserLoginToken
    @GetMapping("/getUserCardByHome")
    public ResponseEntity getUserCardByHome(HttpServletRequest request){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(userId != null){
            UserCard userCard = userCardService.getById(userId);
            //判断第一次有没有数据，没有新增
            if(userCard == null){
                UserInfo userInfo = userInfoService.getById(userId);
                userCard = new UserCard();
                userCard.setUserId(userInfo.getId());
                userCard.setEmail(userInfo.getEmail());
                userCardService.save(userCard);
            }
            //然后查出来
            ResultData userCard1 = userCardService.getUserCard(userId);
            return Response.ok(userCard1);
        }
        return Response.badRequest();
    }
}
