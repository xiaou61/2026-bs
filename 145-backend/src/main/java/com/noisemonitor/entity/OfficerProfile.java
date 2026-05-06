package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("stock_item")
public class OfficerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String stockNo;
    private String consumableName;
    private String labName;
    private Integer currentQty;
    private Integer lockedQty;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






