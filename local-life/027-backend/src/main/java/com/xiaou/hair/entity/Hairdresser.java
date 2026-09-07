package com.xiaou.hair.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 理发师实体类
 */
@Data
@TableName("hairdresser")
public class Hairdresser {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long storeId;
    
    private String name;
    
    private Integer gender;
    
    private String workNo;
    
    private String avatar;
    
    private String skills;
    
    private Integer workYears;
    
    private String intro;
    
    private BigDecimal rating;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
