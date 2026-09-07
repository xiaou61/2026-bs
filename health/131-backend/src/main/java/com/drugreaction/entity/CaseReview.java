package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("case_review")
public class CaseReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reviewNo;
    private String reportNo;
    private String reviewerName;
    private String reviewOpinion;
    private String reviewTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
