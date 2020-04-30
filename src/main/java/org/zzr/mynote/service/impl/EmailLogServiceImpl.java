package org.zzr.mynote.service.impl;

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

}
