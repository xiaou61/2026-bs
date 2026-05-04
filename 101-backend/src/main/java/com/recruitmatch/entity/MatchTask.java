package com.recruitmatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("match_task")
public class MatchTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private Long candidateId;
    private Long jobId;
    private String taskName;
    private String priority;
    private Integer status;
    private Long handlerId;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
