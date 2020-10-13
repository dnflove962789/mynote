package org.zzr.mynote.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 笔记
 * </p>
 *
 * @author zzr
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("note_info")
public class NoteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 笔记本Id
     */
    @TableField("notesId")
    private Integer notesId;

    /**
     * 标题
     */
    private String title;

    /**
     * 纯文本笔记内容
     */
    @TableField(value = "textValue")
    private String textValue;

    /**
     * html格式的笔记内容
     */
    @TableField(value = "htmlValue")
    private String htmlValue;

    /**
     * 排序
     */
    private Integer sort;

    @TableField(value = "createTime", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("isDelete")
    private Integer isDelete;

    @TableField(value = "updateTime", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField("updateUserId")
    private Integer updateUserId;

    /**
     * 笔记类型 0:富文本笔记 1:MarkDown笔记
     */
    private Integer type;


}
