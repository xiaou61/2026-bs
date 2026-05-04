package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EvidenceMaterial {
    private Long id;
    private Long caseId;
    private String materialName;
    private String materialType;
    private String fileUrl;
    private Long submitterId;
    private Integer verifyStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
