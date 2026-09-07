package com.autorepair.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintVO {
    private Long id;
    private Long orderId;
    private Long userId;
    private Long targetUserId;
    private Integer orderStatusSnapshot;
    private String type;
    private String content;
    private String images;
    private Integer status;
    private String reply;
    private Long replyAdminId;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
    private String orderNo;
    private String userName;
    private String targetUserName;
    private String serviceTitle;
}



