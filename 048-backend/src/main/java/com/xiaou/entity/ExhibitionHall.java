package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("exhibition_hall")
public class ExhibitionHall {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer floor;
    private BigDecimal area;
    private Integer capacity;
    private String description;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
