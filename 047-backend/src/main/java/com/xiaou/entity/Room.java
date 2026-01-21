package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("room")
public class Room {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long shopId;
    private String name;
    private Integer capacity;
    private String description;
    private BigDecimal price;
    private Integer status;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}
