package org.zzr.mynote.service;

import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.UserCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户名片表 服务类
 * </p>
 *
 * @author zzr
 * @since 2020-06-08
 */
public interface IUserCardService extends IService<UserCard> {

    public ResultData setUserCard(int userId, String nickName, String email, String label);

    public ResultData getUserCard(int userId);
}
