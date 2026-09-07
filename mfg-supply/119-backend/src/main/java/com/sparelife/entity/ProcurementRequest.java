package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("procurement_request")
public class ProcurementRequest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String requestNo;
    private String partName;
    private Integer requestQty;
    private BigDecimal budgetAmount;
    private String applicantName;
    private String approverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
