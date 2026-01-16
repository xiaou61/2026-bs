package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("time_slot")
public class TimeSlot {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long counselorId;
    private LocalDate slotDate;
    private LocalTime slotTime;
    private Integer duration;
    private String status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
