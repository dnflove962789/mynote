package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.FileUtils;
import org.zzr.mynote.common.util.StringUtils;
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

    /**
     *设置用户名片，只设置有值的字段，用户不存在时创建名片
     * @param userId
     * @param nickName
     * @param email
     * @param label
     * @return
     */
    public ResultData setUserCard(int userId, String nickName, String email, String label){

        UserCard userCard = userCardMapper.selectOne(Wrappers.lambdaQuery(UserCard.class)
            .eq(UserCard::getUserId, userId));

        userCard.setNickName(nickName);
        userCard.setEmail(email);
        userCard.setLabel(label);

        if(userCard != null){
            userCardMapper.updateById(userCard);
        }else{
            userCardMapper.insert(userCard);
        }
        return new ResultData().success();
    }

    /**
     * 获取用户名片
     * @param userId
     * @return
     */
    public ResultData getUserCard(int userId){
        UserCard userCard = userCardMapper.selectOne(Wrappers.lambdaQuery(UserCard.class)
                .eq(UserCard::getUserId, userId));
        if(userCard != null){
            String imgName = userCard.getPortraitName();
            if(StringUtils.isNotEmpty(imgName)){
                userCard.setProtraitUrl(FileUtils.getImgUrl(imgName));
                userCard.setProtraitThumUrl(FileUtils.getThumUrl(imgName));
            }
        }
        return new ResultData().fail().message("用户名片不存在");
    }
}
