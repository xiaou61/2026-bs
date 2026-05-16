package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exam_score")
public class ExamScore {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String scoreNo;
    private String examName;
    private String learnerName;
    private Integer scoreValue;
    private String evaluatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



