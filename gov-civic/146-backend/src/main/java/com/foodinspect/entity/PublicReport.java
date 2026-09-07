package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PublicReport {
    private Long id;
    private String reportNo;
    private String foodName;
    private String reportTitle;
    private String publishChannel;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






