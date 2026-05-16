package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("learner_profile")
public class LearnerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String learnerNo;
    private String learnerName;
    private String departmentName;
    private String positionName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



