package org.zzr.mynote.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户名片表
 * </p>
 *
 * @author zzr
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_card")
public class UserCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userId")
    private Integer userId;

    @TableField("nickName")
    private String nickName;

    /**
     * 展示在名片上的邮箱地址
     */
    private String email;

  /*  *//**
     * 头像原图
     *//*
    @TableField("imgUrl")
    private String imgUrl;*/

    /**
     * 用户头像最初的文件名
     */
    @TableField("portraitOriginName")
    private String portraitOriginName;

    /**
     * 用户头像名称
     */
    @TableField("portraitName")
    private String portraitName;

    /**
     * 头像缩略图
     *//*
    @TableField("thumUrl")
    private String thumUrl;*/

    /**
     * 个人签名
     */
    @TableField("label")
    private String label;

    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "updateTime", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("updateUserId")
    private Integer updateUserId;

    @TableField(exist = false)
    private String protraitUrl;

    @TableField(exist = false)
    private String protraitThumUrl;
}
