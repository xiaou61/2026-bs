package com.homeenergy.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class SavingSuggestion {
    private Long id;
    private String suggestionNo;
    private String homeNo;
    private String deviceName;
    private String suggestionType;
    private BigDecimal savingKwh;
    private String analystName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
