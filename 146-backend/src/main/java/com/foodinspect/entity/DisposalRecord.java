package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DisposalRecord {
    private Long id;
    private String paperNo;
    private String projectNo;
    private String paperTitle;
    private String journalName;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






