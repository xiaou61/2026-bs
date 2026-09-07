package com.fraud.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogVO {
    private Long id;
    private String module;
    private String action;
    private Long operatorId;
    private String operatorName;
    private String operatorRole;
    private String bizNo;
    private String content;
    private LocalDateTime createTime;
}
