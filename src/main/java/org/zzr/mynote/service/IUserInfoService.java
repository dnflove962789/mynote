package org.zzr.mynote.service;

import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
public interface IUserInfoService extends IService<UserInfo> {

    public ResultData registerUser(UserInfo userInfo, String code);

    public ResultData login(UserInfo userInfo);

    ResultData loginWithCode(String email,int type,String code);

}
