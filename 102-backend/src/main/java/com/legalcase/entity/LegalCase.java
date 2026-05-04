package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LegalCase {
    private Long id;
    private String caseNo;
    private Long clientId;
    private Long lawyerId;
    private String title;
    private String caseType;
    private String priority;
    private Integer status;
    private String summary;
    private String currentStage;
    private String nextAction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
