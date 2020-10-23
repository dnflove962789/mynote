package org.zzr.mynote.service.impl;

import org.zzr.mynote.entity.ErrorLog;
import org.zzr.mynote.mapper.ErrorLogMapper;
import org.zzr.mynote.service.IErrorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-10-14
 */
@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog> implements IErrorLogService {

}
