package com.greenhouse.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RemoteCommand {
    private Long id;
    private String commandNo;
    private String deviceNo;
    private String commandType;
    private String paramText;
    private String operatorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
