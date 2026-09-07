package com.mfg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("quality_inspection")
public class QualityInspection {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer inspectQuantity;
    private Integer qualifiedQuantity;
    private Integer unqualifiedQuantity;
    private String result;
    private String inspector;
    private String remark;
    private LocalDateTime inspectTime;

    @TableField(exist = false)
    private String orderNo;
    @TableField(exist = false)
    private String productName;
}
