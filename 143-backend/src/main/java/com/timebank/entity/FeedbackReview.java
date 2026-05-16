package com.timebank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("feedback_review")
public class FeedbackReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String feedbackNo;
    private String projectName;
    private String feedbackType;
    private String reviewTarget;
    private Integer satisfactionLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

