package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FoodItem {
    private Long id;
    private String foodNo;
    private String foodName;
    private String foodType;
    private String batchNo;
    private String inspectionStatus;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






