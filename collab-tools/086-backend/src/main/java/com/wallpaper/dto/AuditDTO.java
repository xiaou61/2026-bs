package com.wallpaper.dto;

import lombok.Data;

@Data
public class AuditDTO {
    private Long wallpaperId;
    private Integer auditStatus;
    private String auditRemark;
}
