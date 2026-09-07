package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("care_plan")
public class CarePlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private String planName;
    private String careContent;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;  // 0-已结束 1-进行中
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
