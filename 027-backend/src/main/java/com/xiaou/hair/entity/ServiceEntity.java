package com.xiaou.hair.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务项目实体类
 */
@Data
@TableName("service")
public class ServiceEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long storeId;
    
    private String category;
    
    private String name;
    
    private BigDecimal price;
    
    private Integer duration;
    
    private String description;
    
    private String image;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
