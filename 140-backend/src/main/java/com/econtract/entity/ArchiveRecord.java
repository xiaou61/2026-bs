package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArchiveRecord {
    private Long id;
    private String archiveNo;
    private String contractTitle;
    private String archiveLocation;
    private String archivistName;
    private String archiveDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



