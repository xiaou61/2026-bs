package com.fraud.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppealVO {
    private Long id;
    private Long alertId;
    private String alertNo;
    private Long userId;
    private String userName;
    private String content;
    private Integer status;
    private String reply;
    private Long replyAdminId;
    private String replyAdminName;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
}
