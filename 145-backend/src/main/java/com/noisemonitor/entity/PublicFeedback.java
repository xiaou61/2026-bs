package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("public_feedback")
public class PublicFeedback {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String feedbackNo;
    private String complaintTitle;
    private String feedbackTopic;
    private String feedbackChannel;
    private String feedbackTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






