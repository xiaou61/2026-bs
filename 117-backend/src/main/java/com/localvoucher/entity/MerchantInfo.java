package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("merchant_info")
public class MerchantInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String merchantName;
    private String merchantNo;
    private String industryName;
    private String contactName;
    private String contactPhone;
    private String settlementCycle;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
