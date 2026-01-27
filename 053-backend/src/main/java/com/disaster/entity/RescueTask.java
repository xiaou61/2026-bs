package com.disaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("rescue_task")
public class RescueTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private Long disasterId;
    private String title;
    private String content;
    private Integer priority;
    private Integer status;
    private Long assigneeId;
    private Long publisherId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String feedback;
    private String feedbackImages;
    private LocalDateTime feedbackTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
