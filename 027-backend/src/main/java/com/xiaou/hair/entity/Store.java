package com.xiaou.hair.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 门店实体类
 */
@Data
@TableName("store")
public class Store {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String address;
    
    private String phone;
    
    private LocalTime openTime;
    
    private LocalTime closeTime;
    
    private String intro;
    
    private String images;
    
    private BigDecimal rating;
    
    private BigDecimal longitude;
    
    private BigDecimal latitude;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
