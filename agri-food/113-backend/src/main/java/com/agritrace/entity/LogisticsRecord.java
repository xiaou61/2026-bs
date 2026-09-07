package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("logistics_record")
public class LogisticsRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String logisticsNo;
    private String batchNo;
    private String fromNode;
    private String toNode;
    private String carrierName;
    private String temperatureText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
