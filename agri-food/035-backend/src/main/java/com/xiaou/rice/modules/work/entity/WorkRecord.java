package com.xiaou.rice.modules.work.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("work_record")
public class WorkRecord extends BaseEntity {
    private Long bookingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal realArea;
    private String photos;
    private String note;
}
