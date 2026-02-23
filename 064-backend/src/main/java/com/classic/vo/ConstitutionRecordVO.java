package com.classic.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConstitutionRecordVO {
    private Long id;
    private Long userId;
    private String userName;
    private String constitutionType;
    private String symptomDesc;
    private String suggestion;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
