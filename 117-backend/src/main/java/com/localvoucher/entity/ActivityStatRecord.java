package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("activity_stat_record")
public class ActivityStatRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String statNo;
    private String activityName;
    private String merchantName;
    private Integer issueCount;
    private Integer receiveCount;
    private Integer verifyCount;
    private BigDecimal gmvAmount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
