package com.psychology.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("counselor")
public class Counselor {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    private String realName;
    private String certificate;
    private String certificateNo;
    private String specialties;
    private String introduction;
    private String style;
    private BigDecimal price;
    private BigDecimal rating;
    private Integer consultCount;
    private String status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
