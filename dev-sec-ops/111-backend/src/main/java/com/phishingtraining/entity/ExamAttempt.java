package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exam_attempt")
public class ExamAttempt {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String attemptNo;
    private String employeeName;
    private String examName;
    private Integer scoreValue;
    private String resultStatus;
    private LocalDateTime submitTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
