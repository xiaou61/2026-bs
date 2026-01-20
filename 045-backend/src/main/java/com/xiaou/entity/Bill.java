package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("bill")
public class Bill {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String billNo;
    private Long elderId;
    private String billMonth;
    private BigDecimal bedFee;
    private BigDecimal careFee;
    private BigDecimal mealFee;
    private BigDecimal otherFee;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Integer status;  // 0-待支付 1-部分支付 2-已支付
    private LocalDate dueDate;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
