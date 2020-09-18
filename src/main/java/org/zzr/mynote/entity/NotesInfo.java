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
 * 笔记本
 * </p>
 *
 * @author zzr
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("notes_info")
public class NotesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    @TableField("subTitle")
    private String subTitle;

    /**
     * 创建日期
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 笔记数量
     */
    @TableField("noteCount")
    private Integer noteCount;

    /**
     * 排序
     */
    private Integer sort = 0;

    @TableField("isDelete")
    private Integer isDelete;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    @TableField("updateUserId")
    private Integer updateUserId;

    private Integer status;


}
