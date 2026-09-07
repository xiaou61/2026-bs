package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("treatment_advice")
public class TreatmentAdvice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String adviceNo;
    private String reportNo;
    private String adviceType;
    private String adviceContent;
    private String doctorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
