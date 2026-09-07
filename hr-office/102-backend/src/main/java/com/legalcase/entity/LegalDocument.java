package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LegalDocument {
    private Long id;
    private Long caseId;
    private Long templateId;
    private String documentNo;
    private String documentTitle;
    private String documentType;
    private String content;
    private Integer status;
    private Long generatedBy;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
