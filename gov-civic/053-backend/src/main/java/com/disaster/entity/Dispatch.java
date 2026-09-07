package com.disaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("dispatch")
public class Dispatch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dispatchNo;
    private Long disasterId;
    private Long fromWarehouseId;
    private String toAddress;
    private BigDecimal toLongitude;
    private BigDecimal toLatitude;
    private Integer priority;
    private Integer status;
    private Long applicantId;
    private Long approverId;
    private LocalDateTime approveTime;
    private String approveRemark;
    private LocalDateTime completeTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
