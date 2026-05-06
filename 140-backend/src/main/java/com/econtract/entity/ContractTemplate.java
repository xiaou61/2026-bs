package com.econtract.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContractTemplate {
    private Long id;
    private String projectNo;
    private String projectName;
    private String leaderName;
    private String collegeName;
    private Integer startYear;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



