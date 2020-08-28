package org.zzr.mynote.service;

import org.springframework.mail.MailException;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.EmailLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzr
 * @since 2020-04-29
 */
public interface IEmailLogService extends IService<EmailLog> {

    ResultData sendSimpleMail(String to) throws MailException;

    ResultData sendResetPasswordEmail(String email,String code,String token);

    boolean checkCode(String email,String code,Integer type);
}
