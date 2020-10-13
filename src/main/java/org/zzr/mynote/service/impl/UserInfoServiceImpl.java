package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.LoginLog;
import org.zzr.mynote.entity.UserCard;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.mapper.UserCardMapper;
import org.zzr.mynote.mapper.UserInfoMapper;
import org.zzr.mynote.service.IEmailLogService;
import org.zzr.mynote.service.ILoginLogService;
import org.zzr.mynote.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

     @Autowired
     private UserInfoMapper userInfoMapper;

     @Autowired
     private EmailLogMapper emailLogMapper;

     @Autowired
     private UserCardMapper userCardMapper;

     @Autowired
     private IEmailLogService emailLogService;

     @Autowired
     private ILoginLogService loginLogService;

     /**
      * 用户注册
      * @param userInfo
      * @param code
      * @return
      */
     public ResultData registerUser(UserInfo userInfo, String code, String ip){
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

               // 密码校验成功后记录登录日志
               loginLogService.save(new LoginLog(selectOne.getId(), ip));
               /*//插入usercard
               UserInfo newSelectOne = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                       .eq(UserInfo::getEmail, userInfo.getEmail())
                       .eq(UserInfo::getType, userInfo.getType()));
               UserCard userCard = new UserCard();
               userCard.setUserId(newSelectOne.getId());
               userCard.setEmail(newSelectOne.getEmail());
               userCardMapper.insert(userCard);*/
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
     public ResultData login(UserInfo userInfo ,String ip){
          UserInfo selectOne = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, userInfo.getEmail())
                  .eq(UserInfo::getType, userInfo.getType())
          );
          if(selectOne == null){
               return new ResultData().fail().message("用户不存在");
          }
          //校验密码
          if(userInfo.getPassword().equals(selectOne.getPassword())){
               // 密码校验成功后记录登录日志
               loginLogService.save(new LoginLog(selectOne.getId(), ip));
               //并返回用户的 JWT
               return new ResultData().success().data(TokenUtils.getTokenForLogin(selectOne));
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
          return new ResultData().success().data(TokenUtils.getTokenForLogin(userInfo));
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

     /**
      * 发送重置密码邮件
      * @param email
      * @param type
      * @return
      */
     public ResultData sendResetPasswordEmail(String email,int type){
          UserInfo userInfo = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
          .eq(UserInfo::getEmail, email).eq(UserInfo::getType, type));
          if(userInfo == null){
               return new ResultData().fail().message("用户不存在");
          }
          String code = StringUtils.getAllCharString(PublicConstant.EMAIL_CODE_LENGTH);
          String token = JwtUtils.getJwtForResetPassword(userInfo,code);
          return emailLogService.sendResetPasswordEmail(email,code,token);
     }

     /**
      * 通过邮件重置密码
      * 校验 JWT，从中解析出 邮箱、用户类型、验证码
      * 然后去 邮件发送记录中校验验证码
      * 校验成功后重置密码
      * @param token
      * @param password
      * @return
      */
     public ResultData resetPasswordByEmail(String token,String password){
          UserInfo userInfo = JwtUtils.getUserInfo(token);
          String userCode = JwtUtils.getCodeForResetPassword(token);

          UserInfo result = userInfoMapper.selectOne(Wrappers.lambdaQuery(UserInfo.class)
                  .eq(UserInfo::getEmail, userInfo.getEmail()).eq(UserInfo::getType, userInfo.getType()));
          if(result == null){
               return new ResultData().fail().message("该邮箱尚未注册");
          }
          if(StringUtils.isEmpty(userCode,result.getEmail())){
               return new ResultData().fail().message("身份验证失败");
          }
          if(emailLogService.checkCode(result.getEmail(), userCode, PublicConstant.RESET_PASSWORD_TYPE)){
               //更新密码
               UserInfo newUpdateUserInfo = new UserInfo();
               newUpdateUserInfo.setId(result.getId());
               newUpdateUserInfo.setPassword(password);
               newUpdateUserInfo.setUpdateTime(LocalDateTime.now());
               newUpdateUserInfo.setUpdateUserId(result.getId());
               userInfoMapper.updateById(newUpdateUserInfo);
               return new ResultData().success();
          }
          return new ResultData().fail().message("密码重置失败，请重试");
     }
}
