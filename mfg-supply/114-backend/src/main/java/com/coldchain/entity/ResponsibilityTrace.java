package com.coldchain.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponsibilityTrace {
    private Long id;
    private String traceNo;
    private String orderNo;
    private String responsibleParty;
    private String nodeName;
    private String reasonText;
    private String impactScope;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
