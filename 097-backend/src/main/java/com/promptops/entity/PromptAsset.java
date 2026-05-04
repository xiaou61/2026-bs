package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("prompt_asset")
public class PromptAsset {
    private Long id;
    private String title;
    private Long categoryId;
    private Long teamId;
    private String scene;
    private String tags;
    private String description;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String teamName;
    @TableField(exist = false)
    private String creatorName;
}
