package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mail_template")
public class MailTemplate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String templateName;
    private String subjectText;
    private String senderName;
    private String baitType;
    private String landingUrl;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
