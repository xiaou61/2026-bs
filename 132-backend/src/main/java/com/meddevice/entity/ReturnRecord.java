package com.meddevice.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReturnRecord {
    private Long id;
    private String returnNo;
    private String borrowNo;
    private String returnTime;
    private String checkResult;
    private String receiverName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
