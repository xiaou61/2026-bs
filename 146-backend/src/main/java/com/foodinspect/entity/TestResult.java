package com.foodinspect.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TestResult {
    private Long id;
    private String resultNo;
    private String foodName;
    private String testConclusion;
    private String testTime;
    private String testerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






