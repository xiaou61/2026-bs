package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("prompt_version")
public class PromptVersion {
    private Long id;
    private Long assetId;
    private String versionNo;
    private String content;
    private String variables;
    private String modelHint;
    private String changeLog;
    private Integer isBaseline;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String assetTitle;
    @TableField(exist = false)
    private String creatorName;
}
