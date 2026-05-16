package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("point_rule")
public class PointRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ruleNo;
    private String projectName;
    private String pointItem;
    private Integer pointValue;
    private String effectiveTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

