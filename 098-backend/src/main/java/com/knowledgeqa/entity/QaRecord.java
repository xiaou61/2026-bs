package com.knowledgeqa.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QaRecord {
    private Long id;
    private Long sessionId;
    private String question;
    private String answer;
    private String sourceChunkIds;
    private BigDecimal confidence;
    private Integer resolved;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
