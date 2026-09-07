package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EvidenceFile {
    private Long id;
    private String evidenceNo;
    private String companyName;
    private String fileName;
    private String fileType;
    private String uploaderName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
