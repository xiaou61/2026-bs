package com.gongkao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("exam_record_answer")
public class ExamRecordAnswer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recordId;
    private Long paperId;
    private Long questionId;
    private String userAnswer;
    private String correctAnswer;
    private BigDecimal score;
    private Integer isCorrect;
    private LocalDateTime createTime;
}

