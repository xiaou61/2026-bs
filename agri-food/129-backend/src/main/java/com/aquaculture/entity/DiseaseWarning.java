package com.aquaculture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("disease_warning")
public class DiseaseWarning {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String warningNo;
    private String pondNo;
    private String warningLevel;
    private String symptomText;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
