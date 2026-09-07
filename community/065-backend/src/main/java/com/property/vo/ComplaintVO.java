package com.property.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComplaintVO {
    private Long id;
    private String orderNo;
    private Long ownerId;
    private String ownerName;
    private String title;
    private String content;
    private Integer status;
    private String reply;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
