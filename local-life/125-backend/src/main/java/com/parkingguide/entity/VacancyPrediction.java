package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("vacancy_prediction")
public class VacancyPrediction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String predictionNo;
    private String lotName;
    private String predictTime;
    private Integer vacancyCount;
    private BigDecimal confidenceScore;
    private String modelVersion;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
