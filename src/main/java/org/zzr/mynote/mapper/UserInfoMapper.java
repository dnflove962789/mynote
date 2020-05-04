package org.zzr.mynote.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.zzr.mynote.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    IPage<UserInfo> selectUserInfoPage(Page<UserInfo> page, @Param(Constants.WRAPPER) Wrapper<UserInfo> wrapper);
}
