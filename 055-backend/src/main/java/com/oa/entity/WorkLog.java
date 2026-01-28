package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("work_log")
public class WorkLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDate logDate;
    private String content;
    private String plan;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String realName;
}
