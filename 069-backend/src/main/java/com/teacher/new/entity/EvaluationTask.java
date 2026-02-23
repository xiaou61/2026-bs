package com.teacher.new.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("evaluation_task")
public class EvaluationTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskName;
    private String termName;
    private Long classId;
    private Long teacherId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
