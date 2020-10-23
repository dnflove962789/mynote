package org.zzr.mynote.service;

import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author zzr
 * @since 2020-10-10
 */
public interface ILoginLogService extends IService<LoginLog> {

    ResultData getCountByDay(String startDate, String endDate);

    ResultData getCountByMonth(String startDate,String endDate);
}
