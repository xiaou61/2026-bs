package com.adoption.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("child_health_record")
public class ChildHealthRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long childId;
    private String healthStatus;
    private String bloodType;
    private String allergyInfo;
    private String vaccinationInfo;
    private String medicalHistory;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
