package org.zzr.mynote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author zzr
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    public LoginLog (Integer userId, String ip){
        this.userId = userId;
        this.ip = ip;
        this.createTime = LocalDateTime.now();
    }

}
