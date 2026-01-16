package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("scale")
public class Scale {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    private String category;
    private String description;
    private Integer questionCount;
    private Integer duration;
    private String status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
