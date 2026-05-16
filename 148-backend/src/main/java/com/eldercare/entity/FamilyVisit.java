package com.eldercare.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FamilyVisit {
    private Long id;
    private String visitNo;
    private String elderName;
    private String visitSubject;
    private String visitMethod;
    private String visitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
