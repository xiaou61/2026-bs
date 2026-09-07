package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evidence_record")
public class EvidenceRecord {
    private Long id;
    private Long registerId;
    private String evidenceNo;
    private String hashValue;
    private String platformName;
    private Integer evidenceStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
