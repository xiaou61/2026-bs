package com.xiaou.rice.modules.dispatch.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaou.rice.common.model.BaseEntity;
import lombok.Data;

@Data
@TableName("dispatch_task")
public class DispatchTask extends BaseEntity {
    private Long bookingId;
    private Long machineId;
    private Long driverId;
    private Long assignedBy;
    private String status;
}
