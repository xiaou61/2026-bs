package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentTemplate {
    private Long id;
    private String templateName;
    private String templateType;
    private String contentVariables;
    private Integer status;
    private Integer usageCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
