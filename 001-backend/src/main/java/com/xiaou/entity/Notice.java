package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公告实体类
 * @author xiaou
 */
@Data
@TableName("notice")
public class Notice implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 分类（通知/活动/系统）
     */
    private String category;
    
    /**
     * 发布者ID
     */
    private Long authorId;
    
    /**
     * 发布者姓名
     */
    @TableField(exist = false)
    private String authorName;
    
    /**
     * 是否置顶（0-否，1-是）
     */
    private Integer isTop;
    
    /**
     * 浏览次数
     */
    private Integer viewCount;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
}

