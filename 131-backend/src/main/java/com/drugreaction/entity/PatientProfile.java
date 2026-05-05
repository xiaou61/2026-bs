package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("patient_profile")
public class PatientProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String patientNo;
    private String patientName;
    private String genderName;
    private Integer ageValue;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
