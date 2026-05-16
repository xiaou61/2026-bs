package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SampleRecord {
    private Long id;
    private String sampleNo;
    private String foodName;
    private String sampleType;
    private String samplingRemark;
    private String recordedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






