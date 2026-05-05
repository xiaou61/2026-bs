package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BorrowRecord {
    private Long id;
    private String borrowNo;
    private String requestNo;
    private String deviceNo;
    private String borrowTime;
    private String borrowerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
