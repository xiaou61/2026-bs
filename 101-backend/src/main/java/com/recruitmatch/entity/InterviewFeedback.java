package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("interview_feedback")
public class InterviewFeedback {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long planId;
    private Long interviewerId;
    private BigDecimal score;
    private Integer resultStatus;
    private String strengths;
    private String weaknesses;
    private String suggestion;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
