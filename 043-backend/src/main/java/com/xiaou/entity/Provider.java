package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("provider")
public class Provider {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String phone;
    private String description;
    private String images;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String status;
    private BigDecimal rating;
    private Integer orderCount;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
