package com.econtract.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractNotice {
    private Long id;
    private String noticeNo;
    private String noticeTitle;
    private String noticeType;
    private String receiverName;
    private String publishTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}



