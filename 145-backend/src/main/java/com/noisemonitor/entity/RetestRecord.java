package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inbound_record")
public class RetestRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String inboundNo;
    private String orderNo;
    private String consumableName;
    private Integer inboundQty;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






