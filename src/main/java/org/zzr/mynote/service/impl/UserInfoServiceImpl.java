package org.zzr.mynote.service.impl;

import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.UserInfoMapper;
import org.zzr.mynote.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
