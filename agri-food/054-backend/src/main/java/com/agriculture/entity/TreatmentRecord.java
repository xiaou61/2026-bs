package com.agriculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("treatment_record")
public class TreatmentRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long planId;
    private Long pestDiseaseId;
    private LocalDate treatmentDate;
    private String method;
    private String medicine;
    private String dosage;
    private Integer effect;
    private BigDecimal cost;
    private Long operatorId;
    private String remark;
    private LocalDateTime createTime;
}
