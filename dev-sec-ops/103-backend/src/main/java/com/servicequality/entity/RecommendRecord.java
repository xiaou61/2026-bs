package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("recommend_record")
public class RecommendRecord {
    @TableId
    private Long id;
    private Long orderId;
    private Long articleId;
    private Long agentId;
    private String matchKeyword;
    private BigDecimal matchScore;
    private Integer adoptStatus;
    private String feedback;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
