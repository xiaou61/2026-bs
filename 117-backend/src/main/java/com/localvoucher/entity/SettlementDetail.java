package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("settlement_detail")
public class SettlementDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String detailNo;
    private String settlementNo;
    private String verifyNo;
    private String couponName;
    private BigDecimal consumeAmount;
    private BigDecimal subsidyAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
