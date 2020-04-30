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
 * 
 * </p>
 *
 * @author zzr
 * @since 2020-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("email_log")
public class EmailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    /**
     * 邮件发送类型,不能为空
     */
    private Integer type;

    /**
     * 收件人，不能为空
     */
    private String email;

    /**
     * 邮件标题，不能为空
     */
    private String title;

    /**
     * 邮件内容，不能为空
     */
    private String content;

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送结果描述，如：发送失败的原因等
     */
    private String result;

    /**
     * 发送状态
     */
    @TableField("statusCode")
    private Integer statusCode;

    /**
     * 发送时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 验证码是否已使用
     */
    private Integer isUsed;


}
