package com.livecommerce.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AfterSaleTicket {
    private Long id;
    private String ticketNo;
    private String orderNo;
    private String customerName;
    private String issueType;
    private String serviceUser;
    private String solutionText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
