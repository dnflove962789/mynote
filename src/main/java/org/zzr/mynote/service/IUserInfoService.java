package org.zzr.mynote.service;

import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.EmailLog;
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

    public ResultData registerUser(UserInfo userInfo, String code, String ip);

    public ResultData login(UserInfo userInfo, String ip);

    public ResultData loginWithCode(EmailLog emailLog, int type);

    ResultData sendResetPasswordEmail(String email,int type);

    ResultData resetPasswordByEmail(String token,String password);
}
