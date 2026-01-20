package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("recycle_order")
public class RecycleOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long collectorId;
    private Long communityId;
    private String address;
    private String contactName;
    private String contactPhone;
    private LocalDateTime appointmentTime;
    private String categoryIds;
    private BigDecimal estimatedWeight;
    private BigDecimal actualWeight;
    private BigDecimal amount;
    private Integer points;
    private Integer status; // 0-待接单，1-已接单，2-进行中，3-已完成，4-已取消
    private String remark;
    private LocalDateTime completeTime;
    private String cancelReason;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
