package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("assessment_exam")
public class AssessmentExam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String examNo;
    private String examName;
    private String courseName;
    private String examDate;
    private Integer participantCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



