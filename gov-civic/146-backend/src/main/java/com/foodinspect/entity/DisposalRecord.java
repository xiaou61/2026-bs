package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DisposalRecord {
    private Long id;
    private String disposalNo;
    private String foodName;
    private String disposalMethod;
    private String disposalTime;
    private String responsibleUnit;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






