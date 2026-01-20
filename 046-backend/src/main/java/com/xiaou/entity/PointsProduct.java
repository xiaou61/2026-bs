package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("points_product")
public class PointsProduct {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String image;
    private String description;
    private Integer points;
    private Integer stock;
    private Integer exchangeCount;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
