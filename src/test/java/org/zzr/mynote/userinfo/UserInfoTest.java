package org.zzr.mynote.userinfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.mapper.UserInfoMapper;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void insert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setType(0);
        userInfo.setEmail("abc@qq.com");
        userInfo.setCreateTime(LocalDateTime.now());
        int rows = userInfoMapper.insert(userInfo);
        System.out.println(rows);
    }

    @Test
    public void selectById(){
        UserInfo userInfo = userInfoMapper.selectById(1);
        System.out.println(userInfo);
    }

    @Test
    public void selectByWrapper(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

    }
}
