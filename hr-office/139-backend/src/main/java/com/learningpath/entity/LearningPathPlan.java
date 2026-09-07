package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("learning_path_plan")
public class LearningPathPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String pathNo;
    private String learnerName;
    private String pathName;
    private Integer stageCount;
    private Integer completedRate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



