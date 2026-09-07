package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultationRecord {
    private Long id;
    private Long caseId;
    private Long clientId;
    private Long lawyerId;
    private String consultType;
    private LocalDateTime consultTime;
    private String question;
    private String answer;
    private String riskLevel;
    private String followAction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
