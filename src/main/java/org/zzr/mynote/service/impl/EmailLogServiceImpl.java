package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.service.IEmailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-04-29
 */
@Service
public class EmailLogServiceImpl extends ServiceImpl<EmailLogMapper, EmailLog> implements IEmailLogService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailLogMapper emailLogMapper;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param to （对方邮箱）
     * @return ResultData
     */
    public ResultData sendSimpleMail(String to)  {
        String subject = "注册用验证码";
        String code = StringUtils.getAllCharString(PublicConstant.EMAIL_CODE_LENGTH);
        String content = "验证码为："+ code;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 邮件发送者
        message.setTo(to); // 邮件接受者
        message.setSubject(subject); // 主题
        message.setText(content); // 内容

        EmailLog emailLog = new EmailLog();
        emailLog.setEmail(to);
        emailLog.setType(PublicConstant.REGISTER_TYPE);
        emailLog.setTitle(subject);
        emailLog.setContent(content);
        emailLog.setCode(code);
        emailLog.setStatusCode(PublicConstant.SUCCESS);
        try {
            //插入emaillog数据
            emailLogMapper.insert(emailLog);
            //发送邮件
            mailSender.send(message);
            return new ResultData().success().message("成功发送邮件");
        } catch (Exception e) {
            e.printStackTrace();
            emailLog.setResult(e.getMessage());
            emailLog.setStatusCode(PublicConstant.FAILED);
            return new ResultData().fail().message("发送邮件失败");
        }
    }


    /**
     * 密码重置邮件
     * @param email
     * @param code
     * @param token
     * @return
     */
    public ResultData sendResetPasswordEmail(String email,String code,String token)  {
        String subject = "来自："+PublicConstant.appName;
        String content = "账号："+email + "重置密码,请点击："+ PublicConstant.webUrl +
                "/ResetPassword/" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 邮件发送者
        message.setTo(email); // 邮件接受者
        message.setSubject(subject); // 主题
        message.setText(content); // 内容

        EmailLog emailLog = new EmailLog();
        emailLog.setEmail(email);
        emailLog.setType(PublicConstant.RESET_PASSWORD_TYPE);
        emailLog.setTitle(subject);
        emailLog.setContent(content);
        emailLog.setCode(code);
        emailLog.setStatusCode(PublicConstant.SUCCESS);
        try {
            //插入emaillog数据
            emailLogMapper.insert(emailLog);
            //发送邮件
            mailSender.send(message);
            return new ResultData().success().message("成功发送邮件");
        } catch (Exception e) {
            e.printStackTrace();
            emailLog.setResult(e.getMessage());
            emailLog.setStatusCode(PublicConstant.FAILED);
            return new ResultData().fail().message("发送邮件失败");
        }
    }

    /**
     * 检验email的code
     * @param email
     * @param code
     * @param type
     * @return
     */
    public boolean checkCode(String email,String code,Integer type){
        List<EmailLog> emailLogs = emailLogMapper.selectList(Wrappers.lambdaQuery(EmailLog.class)
                .eq(EmailLog::getType, type)
                .eq(EmailLog::getEmail, email)
                .eq(EmailLog::getStatusCode, 0).orderByDesc(EmailLog::getId));
        if(emailLogs == null || emailLogs.size() <= 0){
            return false;
        }
        EmailLog emailLog = emailLogs.get(0);

        LocalDateTime createTime = emailLog.getCreateTime();
        LocalDateTime now = LocalDateTime.now();
        long epochSecondNow = now.toEpochSecond(ZoneOffset.of("+8"));
        long epochSecondCreated = createTime.toEpochSecond(ZoneOffset.of("+8"));
        if((epochSecondNow - epochSecondCreated) > 60*5){
            //code过期了
            return false;
        }
        if(emailLog.getCode().equals(code)){
           return true;
        }else{
            return false;
        }
    }

}
