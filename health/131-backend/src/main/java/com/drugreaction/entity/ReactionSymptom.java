package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("reaction_symptom")
public class ReactionSymptom {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String symptomNo;
    private String reportNo;
    private String symptomName;
    private String onsetTime;
    private String symptomLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
