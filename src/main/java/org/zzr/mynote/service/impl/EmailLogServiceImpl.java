package org.zzr.mynote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.service.IEmailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        } catch (MailException e) {
            e.printStackTrace();
            emailLog.setResult(e.getMessage());
            emailLog.setStatusCode(PublicConstant.FAILED);
            return new ResultData().fail().message("发送邮件失败");
        }


    }

}
