package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("competency_profile")
public class CompetencyProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String profileNo;
    private String learnerName;
    private String competencyLevel;
    private Integer gapCount;
    private String evaluatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



