package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exam_question")
public class ExamQuestion {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String questionTitle;
    private String examName;
    private String questionType;
    private String answerKey;
    private Integer scoreValue;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
