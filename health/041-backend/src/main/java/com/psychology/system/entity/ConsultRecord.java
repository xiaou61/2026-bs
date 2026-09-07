package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("consult_record")
public class ConsultRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long appointmentId;
    private Long counselorId;
    private Long userId;
    private String recordContent;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
