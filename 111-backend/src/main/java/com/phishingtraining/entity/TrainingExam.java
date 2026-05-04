package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("training_exam")
public class TrainingExam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String examName;
    private String examCode;
    private String courseName;
    private Integer passScore;
    private String ownerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
