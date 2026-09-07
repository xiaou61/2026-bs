package com.charity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("fund_record")
public class FundRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordType;
    private BigDecimal amount;
    private Long relatedId;
    private String relatedType;
    private String purpose;
    private Long operatorId;
    private LocalDate recordDate;
    private String remark;
    private LocalDateTime createTime;
}
