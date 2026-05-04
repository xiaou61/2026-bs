package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mail_send_record")
public class MailSendRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String campaignName;
    private String employeeName;
    private String email;
    private LocalDateTime sendTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
