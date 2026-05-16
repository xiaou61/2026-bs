package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("training_program")
public class TrainingProgram {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String programNo;
    private String programName;
    private String targetRole;
    private String organizerName;
    private Integer sessionCount;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



