package com.gongkao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("exam_record")
public class ExamRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long paperId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Integer durationSeconds;
    private BigDecimal totalScore;
    private BigDecimal objectiveScore;
    private BigDecimal subjectiveScore;
    private Integer passStatus;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

