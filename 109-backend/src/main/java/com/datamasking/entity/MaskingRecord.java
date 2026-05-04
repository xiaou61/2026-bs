package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("masking_record")
public class MaskingRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String taskNo;
    private String fieldName;
    private String beforeSample;
    private String afterSample;
    private Integer processedCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
