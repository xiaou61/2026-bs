package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("warning_rule")
public class PointRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ruleNo;
    private String categoryName;
    private Integer minStock;
    private String warningLevel;
    private String noticeTarget;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}





