package com.xiaou.wedding.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CostumeBorrow {
    private Long id;
    private Long costumeId;
    private Long orderId;
    private Long customerId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private String status;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
