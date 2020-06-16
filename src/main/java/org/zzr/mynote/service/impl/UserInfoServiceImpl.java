package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.zzr.mynote.common.configuration.PublicConstant;
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

     /**
      * 用户注册
      * @param userInfo
      * @param code
      * @return
      */
     public ResultData registerUser(UserInfo userInfo, String code){
          EmailLog emailLog = new EmailLog();
          emailLog.setEmail(userInfo.getEmail());
          emailLog.setType(PublicConstant.REGISTER_TYPE);
          emailLog.setCode(code);
          //先检查验证码
          if(!verifyEmailCode(emailLog)){
               return new ResultData().fail().message("验证码错误或已过期,请重新获取");
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
      * 普通用户密码登录
      * @param userInfo
      * @return
      */
     public ResultData login(UserInfo userInfo){
          UserInfo selectOne = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, userInfo.getEmail())
                  .eq(UserInfo::getType, userInfo.getType())
          );
          if(selectOne == null){
               return new ResultData().fail().message("用户不存在");
          }
          //校验密码
          if(userInfo.getPassword().equals(selectOne.getPassword())){
               return new ResultData().success().data(JwtUtils.getJwtString(userInfo));
          }
          return new ResultData().fail().message("账号或密码错误");
     }

     /**
      * 使用邮箱验证码登录
      * @param emailLog
      * @param type
      * @return
      */
     public ResultData loginWithCode(EmailLog emailLog, int type){

          //先检查验证码
          if(!verifyEmailCode(emailLog)){
               return new ResultData().fail().message("验证码错误或已过期,请重新获取");
          }

          UserInfo userInfo = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, emailLog.getEmail())
                  .eq(UserInfo::getType, type)
          );
          if(userInfo == null){
               return new ResultData().fail().message("用户不存在");
          }
          return new ResultData().success().data(JwtUtils.getJwtString(userInfo));
     }

     /**
      * 查询邮件log表有无对应的数据
      * @param emailLog
      * @return
      */
     public boolean verifyEmailCode(EmailLog emailLog){
          //先判断code是否正确和是否过期
          List<EmailLog> emailLogs = emailLogMapper.selectList(Wrappers.lambdaQuery(EmailLog.class)
                  .eq(EmailLog::getEmail, emailLog.getEmail())
                  .eq(EmailLog::getType, emailLog.getType())
                  .orderByDesc(EmailLog::getId)
          );
          if(emailLogs.size() == 0){
               return false;
          }
          //拿到最新的email
          emailLog = emailLogs.get(0);
          LocalDateTime createTime = emailLog.getCreateTime();
          LocalDateTime now = LocalDateTime.now();
          long epochSecondNow = now.toEpochSecond(ZoneOffset.of("+8"));
          long epochSecondCreated = createTime.toEpochSecond(ZoneOffset.of("+8"));
          if((epochSecondNow - epochSecondCreated) > 60*5){
               //code过期了
               return false;
          }
          return true;
     }
}
