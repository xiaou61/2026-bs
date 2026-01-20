package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fee_item")
public class FeeItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer category;  // 1-床位费 2-护理费 3-餐费 4-其他
    private BigDecimal price;
    private String unit;
    private String description;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
