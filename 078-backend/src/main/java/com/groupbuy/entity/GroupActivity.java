package com.groupbuy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("group_activity")
public class GroupActivity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long merchantId;
    private BigDecimal groupPrice;
    private Integer minCount;
    private Integer maxCount;
    private Integer limitHours;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private Product product;
    @TableField(exist = false)
    private String merchantName;
}
