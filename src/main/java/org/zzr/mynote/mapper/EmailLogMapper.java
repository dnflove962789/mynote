package org.zzr.mynote.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.zzr.mynote.entity.EmailLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zzr.mynote.entity.UserInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzr
 * @since 2020-04-29
 */
public interface EmailLogMapper extends BaseMapper<EmailLog> {

}
