package org.zzr.mynote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.DateUtil;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.entity.LoginLog;
import org.zzr.mynote.mapper.LoginLogMapper;
import org.zzr.mynote.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-10-10
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Resource
    private LoginLogMapper mapper;


    /**
     * 拿到日期时间段的数据
     * @param startDate 开始日期，格式 YYYY-MM-DD
     * @param endDate   截止日期，格式 YYYY-MM-DD
     * @return
     */
    public ResultData getCountByDay(String startDate, String endDate){

        QueryWrapper<LoginLog> loginLogQueryWrapper = new QueryWrapper<>();
        loginLogQueryWrapper
                .select("DATE_FORMAT(createTime,'%Y-%m-%d') as time","count(1) count")
                .between("createTime", startDate, endDate)
                .groupBy("time")
                .orderByAsc("time");
        List<Map<String, Object>> maps = mapper.selectMaps(loginLogQueryWrapper);
        return new ResultData().success().data(maps);
    }

    /**
     * 含前含后
     * @param startDate 开始日期，格式 YYYY-MM-DD
     * @param endDate   截止日期，格式 YYYY-MM-DD
     * @return
     */
    public ResultData getCountByMonth(String startDate,String endDate){
        QueryWrapper<LoginLog> loginLogQueryWrapper = new QueryWrapper<>();
        loginLogQueryWrapper
                .select("DATE_FORMAT(createTime,'%Y-%m-%d') as time","count(1) count")
                .between("createTime", DateUtil.getFirstDateInMonthByTime(startDate), DateUtil.getLastDateInMonthByTime(endDate))
                .groupBy("time")
                .orderByAsc("time");
        List<Map<String, Object>> maps = mapper.selectMaps(loginLogQueryWrapper);
        return new ResultData().success().data(maps);
    }
}
