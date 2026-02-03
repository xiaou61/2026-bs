package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("work")
public class Work {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long competitionId;
    private Long userId;
    private String title;
    private String content;
    private Integer wordCount;
    private String attachment;
    private Integer status;
    private String rejectReason;
    private BigDecimal finalScore;
    @TableField("`rank`")
    private Integer rank;
    private LocalDateTime submitTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String competitionTitle;
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private Integer scoredCount;
}
