package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentVersion {
    private Long id;
    private Long documentId;
    private String versionNo;
    private String content;
    private String changeSummary;
    private Long operatorId;
    private LocalDateTime createTime;
}
