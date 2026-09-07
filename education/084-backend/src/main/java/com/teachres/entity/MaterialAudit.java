package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialAudit {
    private Long id;
    private Long materialId;
    private Integer auditStatus;
    private String auditRemark;
    private Long auditorId;
    private LocalDateTime auditTime;
}
