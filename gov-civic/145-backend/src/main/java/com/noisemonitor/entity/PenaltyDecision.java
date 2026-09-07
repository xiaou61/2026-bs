package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("penalty_decision")
public class PenaltyDecision {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String penaltyNo;
    private String complaintTitle;
    private String penaltyType;
    private String penaltyTarget;
    private BigDecimal penaltyAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






