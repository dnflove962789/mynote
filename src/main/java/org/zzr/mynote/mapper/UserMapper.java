package org.zzr.mynote.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.zzr.mynote.common.zzrmybatis.BaseMapper;
import org.zzr.mynote.entity.UserInfo;

import java.util.List;

/**
 * 用 @Mapper 注解将 UserMapper 接口标注为一个持久层组件，交给 Spring 管理
 * 持久层组件只负责执行增删改查操作，不参与业务逻辑处理
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    //用 @Select 注解为 selectAll() 方法配置一条查询语句 “select * from user_info”，并将该查询语句的结果映射为 List<UserInfo> 对象
    @Select("select id, type, email, password, createTime, status, isDelete, updateTime, updateUserId " +
            "from user_info")
    List<UserInfo> selectAll();
}
