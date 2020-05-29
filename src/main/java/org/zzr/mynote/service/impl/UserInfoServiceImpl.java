package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.mapper.UserInfoMapper;
import org.zzr.mynote.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

     @Autowired
     private UserInfoMapper userInfoMapper;

     @Autowired
     private EmailLogMapper emailLogMapper;

     public ResultData registerUser(UserInfo userInfo, String code){
          //先判断code是否正确和是否过期
          List<EmailLog> emailLogs = emailLogMapper.selectList(Wrappers.lambdaQuery(EmailLog.class)
                  .eq(EmailLog::getEmail, userInfo.getEmail())
                  .eq(EmailLog::getType, userInfo.getType())
                  .orderByDesc(EmailLog::getId)
          );
          if(emailLogs.size() == 0){
               return new ResultData().fail().message("没有对应的验证码");
          }
          //拿到最新的email
          EmailLog emailLog = emailLogs.get(0);
          LocalDateTime createTime = emailLog.getCreateTime();
          LocalDateTime now = LocalDateTime.now();
          long epochSecondNow = now.toEpochSecond(ZoneOffset.of("+8"));
          long epochSecondCreated = createTime.toEpochSecond(ZoneOffset.of("+8"));
          if((epochSecondNow - epochSecondCreated) > 60*5){
               //code过期了
               return new ResultData().fail().message("验证码已过期");
          }

          //先查询，再插入
          UserInfo selectOne = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, userInfo.getEmail())
                  .eq(UserInfo::getType, userInfo.getType())
          );
          if(selectOne == null){
               //可以插入
               userInfoMapper.insert(userInfo);
               return new ResultData().success().message("账号注册成功");
          }else{
               return new ResultData().fail().message("账号已注册");
          }

     }

     /**
      * 登录
      * @param userInfo
      * @return
      */
     public ResultData login(UserInfo userInfo){
          UserInfo selectOne = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, userInfo.getEmail())
                  .eq(UserInfo::getType, userInfo.getType())
          );
          if(selectOne == null){
               return new ResultData().fail().message("该用户尚未注册");
          }
          //校验密码
          if(userInfo.getPassword().equals(selectOne.getPassword())){
               return new ResultData().success().data(JwtUtils.getJwtString(userInfo));
          }
          return new ResultData().fail().message("账号或密码错误");
     }

     public ResultData loginWithCode(String email,int type,String code){
          return null;
     }
}
