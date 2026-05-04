package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("test_case")
public class PromptTestCase {
    private Long id;
    private Long assetId;
    private String title;
    private String inputText;
    private String expectedOutput;
    private String scorePoints;
    private String difficulty;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String assetTitle;
    @TableField(exist = false)
    private String creatorName;
}
