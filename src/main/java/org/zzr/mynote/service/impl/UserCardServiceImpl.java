package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.UserCard;
import org.zzr.mynote.mapper.UserCardMapper;
import org.zzr.mynote.service.IUserCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户名片表 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-06-08
 */
@Service
public class UserCardServiceImpl extends ServiceImpl<UserCardMapper, UserCard> implements IUserCardService {

    @Autowired
    private UserCardMapper userCardMapper;

    public ResultData setUserCar(int userId, String nickName, String email, String label){

        UserCard userCard = userCardMapper.selectOne(Wrappers.lambdaQuery(UserCard.class)
            .eq(UserCard::getUserId, userId));
        if(userCard != null){

        }else{
            userCardMapper.insert();
        }

    }
}
