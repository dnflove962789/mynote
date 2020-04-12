package org.zzr.mynote.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.zzr.mynote.common.zzrmybatis.BaseEntity;
import org.zzr.mynote.common.zzrmybatis.annoation.TableAttribute;

import java.util.Date;

/**
 * Author:UserInfo
 * Version:2020/4/11 & 1.0
 */
@Data
@TableAttribute(name="user_info")
public class UserInfo extends BaseEntity {
    private int id;
    private Integer type;
    private String email;
    private String password;
    private Date createTime = new Date();
    private Integer status ;
    private Integer isDelete;
    private Date updateTime = new Date();
    private Integer updateUserId;
}
