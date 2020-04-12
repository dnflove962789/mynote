package org.zzr.mynote.service;

import org.springframework.stereotype.Service;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:UserService
 * Version:2020/4/11 & 1.0
 */
@Service
public class UserService {
    // 使用 @Resource 将 UserMapper 组件注入进来，在后续代码中可以直接使用 userMapper 来执行有关 user_info 表的增删改查操作
    @Resource
    private UserMapper userMapper;

    // 调用 UserMapper 组件的 selectAll() 方法查询所有用户数据
    public List<UserInfo> getAll(){
        return userMapper.baseSelectAll(new UserInfo());
    }
}
