package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("evaluation_record")
public class EvaluationRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private Long evaluatorId;
    private Long teacherId;
    private Long classId;
    private BigDecimal attitudeScore;
    private BigDecimal contentScore;
    private BigDecimal methodScore;
    private BigDecimal manageScore;
    private BigDecimal homeworkScore;
    private BigDecimal totalScore;
    private String commentText;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
