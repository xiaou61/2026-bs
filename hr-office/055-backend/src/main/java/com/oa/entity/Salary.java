package com.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("salary")
public class Salary {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String yearMonth;
    private BigDecimal basicSalary;
    private BigDecimal bonus;
    private BigDecimal allowance;
    private BigDecimal deduction;
    private BigDecimal socialSecurity;
    private BigDecimal actualSalary;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String realName;
}
