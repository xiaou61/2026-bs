package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("shop_script")
public class ShopScript {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long shopId;
    private Long scriptId;
    private BigDecimal price;
    private Integer playCount;
    private Integer status;
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}
