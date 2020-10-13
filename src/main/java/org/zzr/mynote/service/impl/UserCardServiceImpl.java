package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.FileUtils;
import org.zzr.mynote.common.util.ImageUtils;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.UserCard;
import org.zzr.mynote.mapper.UserCardMapper;
import org.zzr.mynote.service.IUserCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                userCard.setProtraitUrl(FileUtils.getPortraitUrl(imgName, userCard.getUserId()));
                userCard.setProtraitThumUrl(FileUtils.getPortraitThumUrl(imgName, userCard.getUserId()));
            }
            return new ResultData().success().data(userCard);
        }
        return new ResultData().fail().message("用户名片不存在");
    }

    public ResultData setPortrait(MultipartFile file, int userId){
        String originalFilename = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String fileName;
        try {
            fileName = ImageUtils.savePortrait(file,userId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultData().fail().message("头像保存失败");
        }
        UserCard userCard = new UserCard();
        userCard.setUserId(userId);
        userCard.setPortraitOriginName(originalFilename);
        userCard.setPortraitName(fileName);
        int i = userCardMapper.updateById(userCard);
        if(i==1){
            return new ResultData().success();
        }else{
            return new ResultData().fail();
        }

    }
}
