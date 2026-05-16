package com.learningpath.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("learning_reminder")
public class LearningReminder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reminderNo;
    private String learnerName;
    private String reminderType;
    private String remindTime;
    private String channelName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



