package com.railway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("after_sale_record")
public class AfterSaleRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private String serviceType;
    private Long targetScheduleId;
    private String targetScheduleInfo;
    private String reason;
    private String reviewStatus;
    private String reviewRemark;
    private LocalDateTime reviewTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
