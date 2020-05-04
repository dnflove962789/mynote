package org.zzr.mynote.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zzr
 * @since 2020-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册邮箱
     */
    private String email;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 用户账号状态，如账号锁定、未激活等
     */
    private Integer status;

    @TableField(value = "isDelete",select = false)
    @TableLogic
    private Integer isDelete =0;

    @TableField(value = "updateTime",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("updateUserId")
    private Integer updateUserId;


}
