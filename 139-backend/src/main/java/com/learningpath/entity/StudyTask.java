package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("study_task")
public class StudyTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String pathName;
    private String taskName;
    private String deadlineTime;
    private String assigneeName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



