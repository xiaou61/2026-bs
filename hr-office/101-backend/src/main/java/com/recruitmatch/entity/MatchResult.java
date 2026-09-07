package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("match_result")
public class MatchResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private Long candidateId;
    private Long jobId;
    private String matchedSkills;
    private String missingSkills;
    private BigDecimal matchScore;
    private String recommendLevel;
    private String conclusion;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
