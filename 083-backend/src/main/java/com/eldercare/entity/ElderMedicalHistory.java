package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("elder_medical_history")
public class ElderMedicalHistory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long elderId;
    private String allergyHistory;
    private String pastHistory;
    private String familyHistory;
    private String medicationHistory;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
