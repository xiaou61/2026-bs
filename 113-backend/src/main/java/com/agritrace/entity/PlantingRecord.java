package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("planting_record")
public class PlantingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String batchNo;
    private String operationType;
    private String operationDate;
    private String operatorName;
    private String detailText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
