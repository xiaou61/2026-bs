package com.esgreport.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StakeholderFeedback {
    private Long id;
    private String feedbackNo;
    private String stakeholderName;
    private String feedbackType;
    private String contentText;
    private String handlerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
