package org.zzr.mynote.userinfo;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.service.IUserInfoService;

import java.util.List;

/**
 * Author:UserInfoServiceTest
 * Version:2020/5/2 & 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {

    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void queryChain(){
        List<UserInfo> list = userInfoService.lambdaQuery().ge(UserInfo::getType, "0").like(UserInfo::getEmail, "雨").list();
        list.forEach(System.out::println);

    }

    @Test
    public void updateChain(){
       /* UpdateWrapper<UserInfo> userInfoUpdateWrapper = new UpdateWrapper<>();
        userInfoUpdateWrapper.eq("email","vc@qq.com").set("status",0);
        boolean rows = userInfoService.update(userInfoUpdateWrapper);*/
        boolean rows = userInfoService.lambdaUpdate()
                .ge(UserInfo::getEmail, "1111@123.com")
                .set(UserInfo::getStatus, 0)
                .update();
        System.out.println(rows);

    }

    @Test
    public void updateById(){
       /* UpdateWrapper<UserInfo> userInfoUpdateWrapper = new UpdateWrapper<>();
        userInfoUpdateWrapper.eq("email","vc@qq.com").set("status",0);
        boolean rows = userInfoService.update(userInfoUpdateWrapper);*/
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setEmail("asdasd@QQ");
        boolean rows = userInfoService.updateById(userInfo);
        System.out.println(rows);

    }

    @Test
    public void removeChain(){
        boolean rows = userInfoService.lambdaUpdate()
                .ge(UserInfo::getType, "0")
                .like(UserInfo::getEmail, "雨")
                .remove();
        System.out.println(rows);

    }

    @Test
    public void removeByFrake(){
        boolean rows = userInfoService.removeById(3);
        System.out.println(rows);

    }
}
