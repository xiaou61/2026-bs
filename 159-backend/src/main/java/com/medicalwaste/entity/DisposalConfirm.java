package com.medicalwaste.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisposalConfirm {
    private Long id;
    private String recordNo;
    private String recordName;
    private String category;
    private String ownerName;
    private String planTime;
    private String status;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
