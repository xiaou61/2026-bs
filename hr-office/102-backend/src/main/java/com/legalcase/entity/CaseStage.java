package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseStage {
    private Long id;
    private Long caseId;
    private String stageName;
    private Integer stageOrder;
    private Integer status;
    private LocalDateTime planDate;
    private LocalDateTime finishDate;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
