package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("defect_image")
public class DefectImage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String imageNo;
    private String defectNo;
    private String fileName;
    private String aiLabel;
    private BigDecimal confidenceScore;
    private String reviewResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
